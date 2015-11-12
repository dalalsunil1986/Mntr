package com.mentor.core;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.facebook.login.LoginManager;
import com.mentor.MentorApp;
import com.mentor.ui.activities.LoginActivitiy;

import javax.inject.Inject;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by Joel on 01/07/2015.
 */
public class MentorErrorHandler implements ErrorHandler {

    private final Application app;

    @Inject
    PreferenceManager preferenceManager;

    public MentorErrorHandler(Application app) {
        this.app = app;
        MentorApp.get(app).getComponent().inject(this);
    }

    @Override
    public Throwable handleError(RetrofitError cause) {

        String errorDescription = "";

        if (cause.getKind() == RetrofitError.Kind.NETWORK) {
            errorDescription = "Network Error";
        } else if (cause.getResponse() == null) {
            errorDescription = "No response";

        } else if (cause.getResponse().getStatus() == 401) {
            LoginManager.getInstance().logOut();
            preferenceManager.clear();

            Intent myIntent = new Intent(app, LoginActivitiy.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            app.startActivity(myIntent);

        } else if (cause.getResponse().getStatus() == 404) {
            errorDescription = "Item was not found.";

        } else {

            errorDescription = "Something went wrong. Try again.";
        }

        return new Exception(errorDescription);

    }
}
