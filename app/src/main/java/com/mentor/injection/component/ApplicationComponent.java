package com.mentor.injection.component;

import com.mentor.api.RetrofitHelper;
import com.mentor.core.MentorErrorHandler;
import com.mentor.injection.module.ApplicationModule;
import com.mentor.ui.activities.LoginActivity;
import com.mentor.ui.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Joel on 12/11/2015.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivitiy);
    void inject(RetrofitHelper retrofitHelper);
    void inject(MentorErrorHandler mentorErrorHandler);

}
