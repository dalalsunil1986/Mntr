package com.mentor.api;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.mentor.BuildConfig;
import com.mentor.api.MentorApiService;
import com.mentor.core.DateTimeConverter;

import org.joda.time.DateTime;



/**
 * Created by Joel on 23/11/2015.
 */
public class MockRetrofitHelper {
    private Application app;

    public MockRetrofitHelper(Application app)
    {
        this.app=app;
    }

    public void newMentorApiService()
    {

    }
}
