package com.mentor.util;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;

import com.mentor.core.HeartbeatService;


/**
 * Created by Joel on 14/10/2015.
 */
public class HeartbeatFixerUtils {
    private static final String TAG = "HeartbeatFixerUtils";

    private static final String PREF_KEY_FIXER_STATE = "fixer_state";
    private static enum NetworkState { UNKNOWN, CONNECTED, DISCONNECTED, }

    private static NetworkState sNetworkState = NetworkState.UNKNOWN;

    private HeartbeatFixerUtils() {}

    public static void scheduleHeartbeatRequest(Context context) {
        Log.d(TAG, "HeartbeatFixerUtils, scheduleHeartbeatRequest, fromNetworkStateChange");

        //5 minutes in milliseconds
        setNextHeartbeatRequest(context,150000);


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setNextHeartbeatRequest(Context context, int intervalMillis) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        long triggerAtMillis = System.currentTimeMillis() + intervalMillis;
        Log.d(TAG, "setNextHeartbeatRequest at: " + DateFormat.format("yyyy-MM-dd hh:mm:ss", triggerAtMillis));
        PendingIntent servicePendingIntent = getServicePendingIntent(context);
        int rtcWakeup = AlarmManager.RTC_WAKEUP;
        if (hasKitkat()) {
            alarmManager.setExact(rtcWakeup, triggerAtMillis, servicePendingIntent);
        } else {
            alarmManager.set(rtcWakeup, triggerAtMillis, servicePendingIntent);
        }
    }


    private static PendingIntent getServicePendingIntent(Context context) {
        return PendingIntent.getService(context, 0, new Intent(context, HeartbeatService.class), 0);
    }



    public static void sendHeartbeatRequest(Context context) {
        context.startService(new Intent(context, HeartbeatService.class));
    }

    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

}