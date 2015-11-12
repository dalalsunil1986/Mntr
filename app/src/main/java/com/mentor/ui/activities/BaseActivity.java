package com.mentor.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mentor.MentorApp;
import com.mentor.injection.component.ApplicationComponent;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected ApplicationComponent applicationComponent() {
        return MentorApp.get(this).getComponent();
    }
}
