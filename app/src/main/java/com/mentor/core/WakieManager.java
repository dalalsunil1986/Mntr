package com.mentor.core;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mentor.services.AlarmService;


/**
 * Created by Joel Dean on 07/06/2015.
 */
public class WakieManager {
    private String TAG = "AlarmIntents";

    public boolean createAlarm( Context context, int alarmIdentifier,String serverAlarmId, long alarmTime )
    {

        Intent intent = new Intent( context, AlarmService.class );
        intent.putExtra( "alarmId", serverAlarmId );
        PendingIntent pendingIntent = PendingIntent.getService(context, alarmIdentifier, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService( Activity.ALARM_SERVICE );

        try
        {
            alarmManager.set( AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent );
        } catch( Exception e )
        {
            Log.e(TAG, e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * cancels an alarm.
     *
     * @param context application context
     * @param alarmIdentifier id of alarm to cancel
     * @return true if cancel successful false if not
     */
    public boolean cancelAlarm(Context context,int alarmIdentifier,String serverAlarmId)
    {
        Intent intent = new Intent( context, AlarmService.class );
        intent.putExtra( "alarmId", serverAlarmId );
        PendingIntent pendingIntent = PendingIntent.getService( context, alarmIdentifier, intent, PendingIntent.FLAG_CANCEL_CURRENT );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService( Activity.ALARM_SERVICE );

        try {
            alarmManager.cancel( pendingIntent );
        } catch( Exception e ) {
            Log.e( TAG, e.getMessage() );
            return false;
        }

        return true;
    }
}