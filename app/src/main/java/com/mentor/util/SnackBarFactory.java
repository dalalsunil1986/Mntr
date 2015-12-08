package com.mentor.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.mentor.R;

/**
 * Created by Joel on 12/11/2015.
 */
public class SnackBarFactory {

    public static Snackbar createSnackbar(Context context, View view, @StringRes int message) {
        Snackbar snackbar = Snackbar.make(view, context.getString(message), Snackbar.LENGTH_SHORT);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        return snackbar;
    }

    public static Snackbar createSnackbar(Context context, View view,String message) {
        Snackbar snackbar = Snackbar.make(view,message, Snackbar.LENGTH_SHORT);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        return snackbar;
    }


    /*
     Returns a null snackbar if internet is available else returns one wih
     message.
     */
    public static Snackbar checkConnectivitySnackbar(Context context,View view)
    {
        if(NetworkUtil.isNetworkConnected(context))
        {
            return null;
        }
        else
        {
            return createSnackbar(context,view,R.string.no_internet);
        }
    }
}