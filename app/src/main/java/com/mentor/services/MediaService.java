package com.mentor.services;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.mentor.listeners.VolumeDecreasedListener;
import com.mentor.ui.activities.AlarmingActivity;
import com.mentor.ui.viewmodels.AlarmingModel;
import com.mentor.util.VolumeChangedObserver;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MediaService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, VolumeDecreasedListener {

    private MediaPlayer mediaPlayer;
    private String LOG_TAG = "MediaService";
    private static int NOTIF_ID = 3234324;

    public static final String MEDIASERVICE = "com.wake.social.services.MediaService.";
    public static final String SERVICE_PLAY = MEDIASERVICE + "PLAY";
    public static final String SERVICE_STOP = MEDIASERVICE + "STOP";
    public static final String SNAPSHOT_SECOND = MEDIASERVICE + "SNAP";
    public static final String STOP_SNAP_COUNT = MEDIASERVICE + "SNAP_STOP";

    private boolean isPrepared = false;
    private boolean isPausedInCall = false;
    private boolean isVibrating = false;
    private boolean mediaPlayerHasStarted = false;
    private int startId;


    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    private AlarmingModel alarmingModel;
    private Bitmap profilePic;
    private Vibrator vibrator;
    private TelephonyManager telephonyManager;
    private PhoneStateListener listener;
    private VolumeChangedObserver volumeChangedObserver;
    private int snapCount;
    private AudioManager audio;
    private Handler timerHandler;
    private int runned = 0;
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (runned == 0) {
                runned++;
            } else if (runned == 1) {
                stopSelf(startId);
            }
            timerHandler.postDelayed(this, 300000);
        }
    };


    Handler snapShotHandler = new Handler();
    Runnable snapShotRunnable = new Runnable() {

        @Override
        public void run() {
            snapCount++;
            Intent intent = new Intent(SNAPSHOT_SECOND);
            intent.putExtra("seconds", snapCount);

            sendBroadcast(intent);

            if (snapCount == 60) {
                stopSnapshotTimer();
            } else {
                snapShotHandler.postDelayed(this, 1000);
            }
        }
    };


    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            startId = msg.arg1;
            onHandleIntent((Intent) msg.obj);
        }
    }

    public MediaService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnInfoListener(this);

        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        timerHandler = new Handler();


        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        // Create a PhoneStateListener to watch for off-hook and idle events
        listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        // Phone going off-hook or ringing, pause the player.
                        if (isPlaying()) {
                            stop();
                            isPausedInCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Phone idle. Rewind a couple of seconds and start playing.
                        if (isPausedInCall) {
                            isPausedInCall = false;
                            prepareThenPlay(alarmingModel.getAlarmId());
                        }
                        break;
                }
            }
        };

        // Register the listener with the telephony manager.
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        HandlerThread thread = new HandlerThread("MediaService:WorkerThread");
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);

        volumeChangedObserver = new VolumeChangedObserver(this, new Handler());
        volumeChangedObserver.setVolumeDecreasedListener(this);
        getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, volumeChangedObserver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Message message = serviceHandler.obtainMessage();
        message.arg1 = startId;
        message.obj = intent;
        serviceHandler.sendMessage(message);


        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(LOG_TAG, "Service exiting");

        stop();

        synchronized (this) {
            if (mediaPlayer != null) {
                if (mediaPlayerHasStarted) {
                    mediaPlayer.release();
                } else {
                    mediaPlayer.setOnCompletionListener(null);
                    mediaPlayer.setOnPreparedListener(null);
                    mediaPlayer.setOnInfoListener(null);
                }
                mediaPlayer = null;
            }
        }
        getContentResolver().unregisterContentObserver(volumeChangedObserver);

        serviceLooper.quit();
        stopForeground(true);


    }

    protected void onHandleIntent(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Log.d(LOG_TAG, "Null intent received");
            return;
        }

        String action = intent.getAction();
        Log.d(LOG_TAG, "Playback service action received: " + action);

        if (action.equals(SERVICE_PLAY)) {
            alarmingModel = intent.getParcelableExtra("data");

            if (alarmingModel == null) {
                Log.d(LOG_TAG, "AlarmingModel is null.");
                return;
            }
            if (!isPlaying()) {
                prepareThenPlay(alarmingModel.getAlarmId());
            }



        } else if (action.equals(SERVICE_STOP)) {
            stopSelfResult(startId);
        } else if (action.equals(STOP_SNAP_COUNT)) {
            stopSnapshotTimer();
        }

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    synchronized private boolean isPlaying() {
        return isPrepared && mediaPlayer.isPlaying();
    }

    synchronized private void stop() {
        Log.d(LOG_TAG, "stop");
        if (isPrepared) {
            isPrepared = false;
            mediaPlayer.stop();
        }

        if (isVibrating) {
            isVibrating = false;
            vibrator.cancel();
        }
    }

    private void startSnapshotTimer() {
        snapShotHandler.postDelayed(snapShotRunnable, 0);
    }

    private void stopSnapshotTimer() {
        snapShotHandler.removeCallbacks(snapShotRunnable);
    }

    private void prepareThenPlay(String alarmId) {
        stop();

        timerHandler.postDelayed(timerRunnable, 0);

        audio.setStreamVolume(AudioManager.STREAM_MUSIC, audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);

        synchronized (this) {
            mediaPlayer.reset();

            File file =null;

                    //Utils.Operations.getMediaFile(alarmId, getApplicationContext(), MediaType.VoiceTones, Extension.MP3);

            if (file != null) {
                try {
                    FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                    mediaPlayer.setDataSource(fis.getFD());

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

            } else {
                Uri defaultTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

                try {
                    mediaPlayer.setDataSource(getApplicationContext(), defaultTone);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d(LOG_TAG, "Preparing: " + alarmId);
            mediaPlayer.prepareAsync();
            Log.d(LOG_TAG, "Waiting for prepare");
            mediaPlayer.setLooping(true);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        }
    }

    synchronized private void play() {
        if (alarmingModel.isVibrate()) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            Log.v(LOG_TAG, "Vibrating.");
            // Start without a delay
            // Vibrate for 100 milliseconds
            // Sleep for 1000 milliseconds
            long[] pattern = {0, 500, 1000};
            vibrator.vibrate(pattern, 0);
            isVibrating = true;
        }

        mediaPlayer.start();
        mediaPlayerHasStarted = true;

        prepareThenPresentPlayingNotif();
    }

    private void prepareThenPresentPlayingNotif() {

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.v("service", "success");
                prepareNotif(bitmap);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.v("service", "failed");
                prepareNotif(((BitmapDrawable) errorDrawable).getBitmap());

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

      //  Picasso.with(this).load(Utils.Operations.facebookPicture(alarmingModel.getWakerFacebookId())).error(R.drawable.default_pic).placeholder(R.drawable.default_pic).into(target);
    }

    public void prepareNotif(Bitmap bitmap) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext())
                //.setLargeIcon(Utils.Operations.getCroppedBitmap(bitmap))
               // .setSmallIcon(R.drawable.notif)
                .setAutoCancel(false)
                .setOngoing(true)
                .setLights(Color.CYAN, 1000, 500)
                .setContentText(alarmingModel.getMessage())
                .setContentTitle(alarmingModel.getWakerName())
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setWhen(System.currentTimeMillis());


        Intent notifyIntent =
                new Intent(MediaService.this, AlarmingActivity.class);
        notifyIntent.putExtra("alarmId", alarmingModel.getAlarmId());
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        MediaService.this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        notification.setContentIntent(notifyPendingIntent);
        startForeground(NOTIF_ID, notification.build());

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.w(LOG_TAG, "onComplete()");

        synchronized (this) {
            if (!isPrepared) {
                // This file was not good and MediaPlayer quit
                Log.w(LOG_TAG,
                        "MediaPlayer refused to play current item. Bailing on prepare.");
            }
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(LOG_TAG, "Prepared");
        synchronized (this) {
            if (mediaPlayer != null) {
                isPrepared = true;
            }
        }
        play();
    }

    @Override
    public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
        Log.w(LOG_TAG, "onInfo(" + arg1 + ", " + arg2 + ")");
        return false;
    }

    @Override
    public void volumeDecreased() {

        audio.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);
    }
}

