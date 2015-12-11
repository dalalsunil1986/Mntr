package com.mentor.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.mentor.R;
import com.mentor.ui.viewmodels.WakieItem;

import org.parceler.Parcel;
import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateWakieActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    WakieItem wakieItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wakie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorAccent));
        }

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("wakie"))
        {
            wakieItem = Parcels.unwrap(getIntent().getParcelableExtra("alarm"));
        }
        else
        {
            
        }



    }

}
