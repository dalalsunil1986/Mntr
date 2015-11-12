package com.mentor.injection.module;

import android.app.Application;

import com.mentor.core.PreferenceManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Joel on 11/11/2015.
 */

@Module
public class ApplicationModule
{
    final Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Provides
    PreferenceManager providePreferenceManager()
    {
        return new PreferenceManager(app);
    }



}
