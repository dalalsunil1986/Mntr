package com.mentor;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.mentor.injection.component.ApplicationComponent;
import com.mentor.injection.component.DaggerApplicationComponent;
import com.mentor.injection.module.ApplicationModule;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.Iconics;

import net.danlew.android.joda.JodaTimeAndroid;

import eu.inloop.easygcm.GcmListener;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Joel on 09/11/2015.
 */
public class MentorApp extends Application implements GcmListener {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        Iconics.registerFont(new CommunityMaterial());


        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public static MentorApp get(Context context) {
        return (MentorApp) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        applicationComponent = applicationComponent;
    }

    /*
    * These methods are triggered when a push notification arrives from
    * the server via GCM
    * */

    @Override
    public void onMessage(String s, Bundle bundle) {

    }

    @Override
    public void sendRegistrationIdToBackend(String s) {

    }
}
