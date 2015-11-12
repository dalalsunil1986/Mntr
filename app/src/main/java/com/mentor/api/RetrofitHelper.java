package com.mentor.api;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.mentor.BuildConfig;
import com.mentor.MentorApp;
import com.mentor.core.DateTimeConverter;
import com.mentor.core.MentorErrorHandler;
import com.mentor.core.PreferenceManager;

import org.joda.time.DateTime;

import javax.inject.Inject;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Joel on 11/11/2015.
 */
public class RetrofitHelper
{
    @Inject
    PreferenceManager preferenceManager;
    private Application app;

    public RetrofitHelper(Application app)
    {
        this.app=app;
        MentorApp.get(app).getComponent().inject(this);
    }

    public MentorApiService newMentorApiService()
    {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {

                request.addHeader("Authorization", "Bearer " + preferenceManager.getBearerToken());
                request.addHeader("User", preferenceManager.getName());
                request.addHeader("UserFacebookId", preferenceManager.getUserFacebookId());
            }
        };

        RestAdapter mentorAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.DEBUG?MentorApiService.TEST_ENDPOINT:MentorApiService.ENDPOINT)
                .setLogLevel(BuildConfig.DEBUG?RestAdapter.LogLevel.FULL: RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(builder.create()))
                .setErrorHandler(new MentorErrorHandler(app))
                .setRequestInterceptor(requestInterceptor)
                .build();

        return mentorAdapter.create(MentorApiService.class);
    }

    public MentorTokenService newMentorTokenService()
    {
        RestAdapter mentorAdapter = new RestAdapter.Builder()
                .setEndpoint(BuildConfig.DEBUG ? MentorTokenService.TEST_ENDPOINT : MentorTokenService.ENDPOINT)
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        return mentorAdapter.create(MentorTokenService.class);
    }

}
