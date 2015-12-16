package com.mentor.util;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;

import com.mentor.listeners.VolumeChangedListener;
import com.mentor.listeners.VolumeDecreasedListener;


/**
 * Created by Joel on 13/08/2015.
 */
public class VolumeChangedObserver extends ContentObserver {
    private int previousVolume;
    private Context context;
    private VolumeDecreasedListener volumeDecreasedListener;
    private VolumeChangedListener volumeChangedListener;


    public VolumeChangedObserver(Context c, Handler handler) {
        super(handler);
        context=c;

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

        int delta=previousVolume-currentVolume;

        //decreased
        if(delta>0)
        {
            if(volumeChangedListener!=null)
            {
                volumeChangedListener.volumeChanged(currentVolume);
            }

            previousVolume=currentVolume;
            if(volumeDecreasedListener!=null)
            {
                volumeDecreasedListener.volumeDecreased();

            }

        }
        //increased
        else if(delta<0)
        {
            previousVolume=currentVolume;
            if(volumeChangedListener!=null)
            {
                volumeChangedListener.volumeChanged(currentVolume);
            }

        }
    }

    public void setVolumeDecreasedListener(VolumeDecreasedListener volumeDecreasedListener) {
        this.volumeDecreasedListener = volumeDecreasedListener;
    }



    public void setVolumeChangedListener(VolumeChangedListener volumeChangedListener) {
        this.volumeChangedListener = volumeChangedListener;
    }
}