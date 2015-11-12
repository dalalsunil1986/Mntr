package com.mentor.api;

import com.google.gson.GsonBuilder;
import com.mentor.core.DateTimeConverter;
import com.mentor.core.MentorErrorHandler;

import org.joda.time.DateTime;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Joel on 11/11/2015.
 */
public class RetrofitHelper
{
    public MentorApiService newMentorApiService()
    {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {

               // request.addHeader("Authorization", "Bearer " + getUserManager().getBearerToken());
                //request.addHeader("User", getUserManager().getName());
                //request.addHeader("UserFacebookId", getUserManager().getKeyFacebookId());
            }
        };

        RestAdapter mentorAdapter = new RestAdapter.Builder()
                .setEndpoint(MentorApiService.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(builder.create()))
                .setErrorHandler(new MentorErrorHandler())
                .setRequestInterceptor(requestInterceptor)
                .build();

        return mentorAdapter.create(MentorApiService.class);

    }

}
