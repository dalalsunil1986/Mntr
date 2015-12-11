package com.mentor.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mentor.R;
import com.mentor.ui.viewmodels.WakieItem;

import org.joda.time.DateTime;
import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateWakieActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    WakieItem wakieItem;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.date)
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wakie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccent));
        }

        ButterKnife.bind(this);
        applicationComponent().inject(this);

        setToolbar(toolbar);

        if (getIntent().hasExtra("wakie")) {
            wakieItem = Parcels.unwrap(getIntent().getParcelableExtra("alarm"));
            setUpWakie(wakieItem);

        } else {
            wakieItem= new WakieItem();
            DateTime now = DateTime.now();
            DateTime futureTime = DateTime.now().plusHours(5).minusMinutes(now.getMinuteOfDay());

            wakieItem.setDate(now);
            wakieItem.setTime(futureTime);

            setUpWakie(wakieItem);

        }
    }

    public void setUpWakie(WakieItem wakieItem) {
        date.setText(wakieItem.getDate().toString("EEEE, MMMM d, yyyy"));
        time.setText(wakieItem.getTime().toString("h:mm"));


    }
}
