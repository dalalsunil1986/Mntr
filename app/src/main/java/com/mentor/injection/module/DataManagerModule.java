package com.mentor.injection.module;

import android.content.Context;

import com.mentor.core.PreferencesHelper;
import com.mentor.injection.scope.PerDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Joel on 11/11/2015.
 */

@Module
public class DataManagerModule
{
    final Context context;

    public DataManagerModule(Context context) {
        this.context = context;
    }

    @Provides
    @PerDataManager
    PreferencesHelper providePreferencesHelper()
    {
        return new PreferencesHelper(context);
    }

}
