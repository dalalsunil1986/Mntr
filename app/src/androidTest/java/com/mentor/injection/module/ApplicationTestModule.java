package com.mentor.injection.module;

import android.app.Application;

import com.algolia.search.saas.APIClient;
import com.mentor.api.MentorApiService;
import com.mentor.api.MentorTokenService;
import com.mentor.core.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    APIClient provideAlgoliaClient()
    {
        return mock(APIClient.class);
    }


}
