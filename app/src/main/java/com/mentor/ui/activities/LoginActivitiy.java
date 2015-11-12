package com.mentor.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mentor.MentorApp;
import com.mentor.R;
import com.mentor.injection.component.ApplicationComponent;

public class LoginActivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activitiy);
        applicationComponent().inject(this);
    }


    protected ApplicationComponent applicationComponent() {
        return MentorApp.get(this).getComponent();
    }
}
