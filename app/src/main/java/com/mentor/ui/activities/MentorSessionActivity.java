package com.mentor.ui.activities;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mentor.R;
import com.opentok.android.Publisher;
import com.opentok.android.Session;

public class MentorSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_session);
    }
}
