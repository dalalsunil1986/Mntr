package com.mentor;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.anupcowkur.reservoir.Reservoir;
import com.facebook.FacebookSdk;
import com.google.gson.GsonBuilder;
import com.mentor.core.DateTimeConverter;
import com.mentor.injection.component.ApplicationComponent;
import com.mentor.injection.component.DaggerApplicationComponent;
import com.mentor.injection.module.ApplicationModule;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.Iconics;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.orm.SugarApp;
import com.squareup.picasso.Picasso;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;

import eu.inloop.easygcm.GcmListener;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Joel on 09/11/2015.
 */
public class MentorApp extends SugarApp implements GcmListener {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        generalInit();
        dependencyInjectionInit();
        fontsInit();
        reservoirInit();
        imageLoadingInit();
        fbInit();
    }

    private void fbInit()
    {
        FacebookSdk.sdkInitialize(this);
    }

    private void imageLoadingInit()
    {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }


            @Override
            public Drawable placeholder(Context ctx, String tag) {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
                }

                //we use the default one for
                //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

                return super.placeholder(ctx, tag);
            }
        });


    }

    private void generalInit() {
        //Date library that needs initializing for timezone changes etc.
        JodaTimeAndroid.init(this);

    }

    /*
    * Creates the dagger component that will be
    * injected throughout the app for dependencies.
    * */
    private void dependencyInjectionInit() {
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
    private void fontsInit() {
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
    private void reservoirInit() {
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        try {
            Reservoir.init(this, 2048, builder.create());
        } catch (Exception e) {
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
