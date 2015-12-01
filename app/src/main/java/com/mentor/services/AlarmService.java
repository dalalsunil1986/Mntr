package com.mentor.services;

import android.content.Intent;

import com.mentor.core.WakefulIntentService;


public class AlarmService extends WakefulIntentService {


    public AlarmService() {
        super("AlarmService");
    }

    @Override
    protected void doWakefulWork(Intent intent) {

    }
}