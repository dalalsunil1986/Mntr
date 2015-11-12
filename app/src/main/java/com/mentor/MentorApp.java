package com.mentor;

import android.app.Application;
import android.os.Bundle;

import net.danlew.android.joda.JodaTimeAndroid;

import eu.inloop.easygcm.GcmListener;

/**
 * Created by Joel on 09/11/2015.
 */
public class MentorApp extends Application implements GcmListener {

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);
    }

    @Override
    public void onMessage(String s, Bundle bundle) {

    }

    @Override
    public void sendRegistrationIdToBackend(String s) {

    }
}
