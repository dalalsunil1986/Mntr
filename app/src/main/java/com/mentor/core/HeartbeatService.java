package com.mentor.core;

import android.content.Intent;
import android.util.Log;

import com.mentor.util.HeartbeatFixerUtils;

public class HeartbeatService extends WakefulIntentService {

    private static final Intent GTALK_HEART_BEAT_INTENT = new Intent("com.google.android.intent.action.GTALK_HEARTBEAT");
    private static final Intent MCS_MCS_HEARTBEAT_INTENT = new Intent("com.google.android.intent.action.MCS_HEARTBEAT");

    public HeartbeatService() {
        super(HeartbeatService.class.getName());
    }


    @Override
    protected void doWakefulWork(Intent intent) {
        Log.v(HeartbeatService.class.getName(), "HeartbeatService sent heartbeat request");
        sendBroadcast(GTALK_HEART_BEAT_INTENT);
        sendBroadcast(MCS_MCS_HEARTBEAT_INTENT);
        HeartbeatFixerUtils.scheduleHeartbeatRequest(this);

    }
}
