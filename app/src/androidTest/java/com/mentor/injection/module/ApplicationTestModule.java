package com.mentor.injection.module;

import android.app.Application;

import com.mentor.api.MentorApiService;
import com.mentor.api.MentorTokenService;
import com.mentor.core.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.mockito.Mockito.mock;

/**
 * Created by Joel on 23/11/2015.
 */
@Module
public class ApplicationTestModule {
    final Application app;

    public ApplicationTestModule(Application app) {
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
        return mock(MentorApiService.class);
    }

    @Provides
    @Singleton
    MentorTokenService provideMentorTokenService() {
        return mock(MentorTokenService.class);
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {

        final RealmConfiguration configuration = new RealmConfiguration.Builder(app)
                .name("test.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0L)
                .build();

        Realm.deleteRealm(configuration);

        return configuration;
    }

    @Provides
    @Singleton
    Realm provideDefaultRealm(RealmConfiguration config) {
        return Realm.getInstance(config);
    }


}
