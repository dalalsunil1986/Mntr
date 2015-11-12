package com.mentor.core;

import android.content.Context;
import android.content.Intent;

import com.facebook.login.LoginManager;
import com.mentor.ui.activities.LoginActivitiy;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by Joel on 01/07/2015.
 */
public class MentorErrorHandler implements ErrorHandler {

    private final Context ctx;

    public MentorErrorHandler( ) {
        ctx = null;
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
            //app.getUserManager().logOut();

            Intent myIntent = new Intent(ctx, LoginActivitiy.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //app.startActivity(myIntent);

        } else if (cause.getResponse().getStatus() == 404)
        {
            errorDescription = "Item was not found.";

        } else {
            //Exception exception = new Exception("API exception : "+cause.getUrl()+" " + cause.getResponse().getStatus() + " " + cause.getResponse().getReason() + " "
           // );
            //Mint.logException(exception);

            errorDescription = "Something went wrong. Try again.";
        }

        return new Exception(errorDescription);

    }
}
