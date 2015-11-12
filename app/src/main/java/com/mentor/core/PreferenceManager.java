package com.mentor.core;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Joel on 11/11/2015.
 */
public class PreferenceManager {
    private static SharedPreferences mPref;

    public static final String PREF_FILE_NAME = "mentor_preferences";


    public PreferenceManager(Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

}
