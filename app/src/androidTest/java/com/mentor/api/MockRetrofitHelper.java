package com.mentor.api;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.mentor.BuildConfig;
import com.mentor.api.MentorApiService;
import com.mentor.core.DateTimeConverter;

import org.joda.time.DateTime;

import retrofit.MockRestAdapter;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Joel on 23/11/2015.
 */
public class MockRetrofitHelper {
    private Application app;

    public MockRetrofitHelper(Application app)
    {
        this.app=app;
    }

    public MockRestAdapter newMentorApiService()
    {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        RestAdapter mentorAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.DEBUG? MentorApiService.TEST_ENDPOINT:MentorApiService.ENDPOINT)
                .setLogLevel(BuildConfig.DEBUG?RestAdapter.LogLevel.FULL: RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(builder.create()))
                .build();

        return MockRestAdapter.from(mentorAdapter);
    }
}
