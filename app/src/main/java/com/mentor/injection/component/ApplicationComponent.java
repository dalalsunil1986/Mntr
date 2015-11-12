package com.mentor.injection.component;

import com.mentor.injection.module.ApplicationModule;
import com.mentor.ui.MainActivity;
import com.mentor.ui.activities.LoginActivitiy;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Joel on 12/11/2015.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(LoginActivitiy loginActivitiy);
}
