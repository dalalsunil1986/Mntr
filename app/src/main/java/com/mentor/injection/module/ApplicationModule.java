package com.mentor.injection.module;

import android.app.Application;

import com.mentor.api.MentorApiService;
import com.mentor.api.MentorTokenService;
import com.mentor.api.RetrofitHelper;
import com.mentor.core.PreferenceManager;
import com.mentor.db.Migration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Joel on 11/11/2015.
 */

@Module
public class ApplicationModule {
    final Application app;

    public ApplicationModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    PreferenceManager providePreferenceManager() {
        return new PreferenceManager(app);
    }

    @Provides
    @Singleton
    MentorApiService provideMentorApiService() {
        return new RetrofitHelper(app).newMentorApiService();
    }

    @Provides
    @Singleton
    MentorTokenService provideMentorTokenService() {
        return new RetrofitHelper(app).newMentorTokenService();
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {
        return new RealmConfiguration.Builder(app)
                //.schemaVersion(Migration.SCHEMA_VERSION)
                //.migration(new Migration())
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    @Provides
    @Singleton
    Realm provideDefaultRealm(RealmConfiguration config) {
        return Realm.getInstance(config);
    }


}








