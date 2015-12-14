package com.mentor.api;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.mentor.BuildConfig;
import com.mentor.MentorApp;
import com.mentor.core.DateTimeConverter;
import com.mentor.core.PreferenceManager;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.joda.time.DateTime;

import java.io.IOException;
import java.net.Proxy;

import javax.inject.Inject;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Joel on 11/11/2015.
 */
public class RetrofitHelper
{
    @Inject
    PreferenceManager preferenceManager;

    public RetrofitHelper(Application app)
    {
        MentorApp.get(app).getComponent().inject(this);
    }

    public MentorApiService newMentorApiService()
    {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        OkHttpClient client = new OkHttpClient();
        client.setAuthenticator(new Authenticator() {
            @Override
            public Request authenticate(Proxy proxy, Response response) throws IOException {

                //broadcast log out here.
                return null;
            }

            @Override
            public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                return null;
            }
        });
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder()
                .addHeader("Authorization", "Bearer " + preferenceManager.getBearerToken())
                .addHeader("User", preferenceManager.getName())
                .build();
                Response response = chain.proceed(request);
                return response;
            }
        });

        client.interceptors().add(new LoggingInterceptor());

        Retrofit retrofit= new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .baseUrl(BuildConfig.DEBUG?MentorApiService.TEST_ENDPOINT:MentorApiService.ENDPOINT)
                .build();

        return retrofit.create(MentorApiService.class);
    }

    public MentorTokenService newMentorTokenService()
    {
        OkHttpClient client = new OkHttpClient();

        client.interceptors().add(new LoggingInterceptor());

        Retrofit retrofit= new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BuildConfig.DEBUG ? MentorTokenService.TEST_ENDPOINT : MentorTokenService.ENDPOINT)
                .build();

        return retrofit.create(MentorTokenService.class);
    }

}
