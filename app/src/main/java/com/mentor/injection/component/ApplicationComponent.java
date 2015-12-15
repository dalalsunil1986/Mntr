package com.mentor.injection.component;

import com.mentor.api.RetrofitHelper;
import com.mentor.injection.module.ApplicationModule;
import com.mentor.ui.activities.BaseActivity;
import com.mentor.ui.activities.CreateWakieActivity;
import com.mentor.ui.activities.LoginActivity;
import com.mentor.ui.activities.MainActivity;
import com.mentor.ui.fragment.AlarmFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Joel on 12/11/2015.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);
    void inject(RetrofitHelper retrofitHelper);
    void inject(BaseActivity baseActivity);
    void inject(CreateWakieActivity createWakieActivity);
    void inject(AlarmFragment alarmFragment);

}
