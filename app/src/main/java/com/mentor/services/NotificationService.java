package com.mentor.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.pubnub.api.Pubnub;

public class NotificationService extends Service {
    Pubnub pubnub;
    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
