package com.mentor;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.GsonBuilder;
import com.mentor.core.DateTimeConverter;
import com.mentor.injection.component.ApplicationComponent;
import com.mentor.injection.component.DaggerApplicationComponent;
import com.mentor.injection.module.ApplicationModule;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.Iconics;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

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

        generalInit();
        dependencyInjectionInit();
        fontsInit();
        reservoirInit();

        }

    private void generalInit()
    {
        //Date library that needs initializing for timezone changes etc.
        JodaTimeAndroid.init(this);

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());

    }

    /*
    * Creates the dagger component that will be
    * injected throughout the app for dependencies.
    * */
    private void dependencyInjectionInit()
    {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public static MentorApp get(Context context) {
        return (MentorApp) context.getApplicationContext();
    }

    /*
    * Calligraphy is used to apply a font to the entire app
    * also as a means of adding custom fonts to views.
    *
    * Iconics allow the usage of font icons instead of using drawables.
    * */
    private void fontsInit()
    {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        Iconics.registerFont(new CommunityMaterial());
    }


    /**
     * Reservoir is used to cache key/value pairs.
     */
    private void reservoirInit()
    {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        try {
            Reservoir.init(this, 2048, builder.create());
        } catch (Exception e) {
            Timber.d(e.getMessage());
        }
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
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
