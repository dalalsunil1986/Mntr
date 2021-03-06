package com.mentor.injection.module;

import android.app.Application;

import com.algolia.search.saas.APIClient;
import com.mentor.R;
import com.mentor.api.MentorApiService;
import com.mentor.api.MentorTokenService;
import com.mentor.api.RetrofitHelper;
import com.mentor.core.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    APIClient provideAlgoliaClient()
    {
        return new APIClient(app.getString(R.string.algolia_application_id),app.getString(R.string.algolia_search_key));
    }


}








