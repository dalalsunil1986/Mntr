package com.mentor.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.style.ForegroundColorSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mentor.R;
import com.mentor.ui.viewmodels.WakieItem;
import com.mentor.util.Spanny;

import org.joda.time.DateTime;
import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class CreateWakieActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    WakieItem wakieItem;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.edit_time)
    TextView editTime;

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
            wakieItem = Parcels.unwrap(getIntent().getParcelableExtra("wakie"));
            setUpWakie(wakieItem);

        } else {
            wakieItem = new WakieItem();
            DateTime now = DateTime.now();
            DateTime futureTime = DateTime.now().plusHours(5).minusMinutes(now.getMinuteOfDay());

            wakieItem.setDate(futureTime);
            wakieItem.setTime(futureTime);

            setUpWakie(wakieItem);

        }
    }

    public void setUpWakie(WakieItem wakieItem) {
        date.setText(wakieItem.getDate().toString("EEEE, MMMM d, yyyy").toUpperCase());

        CalligraphyTypefaceSpan regularSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "Roboto-Light.ttf"));
        CalligraphyTypefaceSpan lightSpan = new CalligraphyTypefaceSpan(TypefaceUtils.load(getAssets(), "Roboto-Thin.ttf"));

        Spanny alarmText = new Spanny()
                .append(wakieItem.getTime().toString("H"), regularSpan, new ForegroundColorSpan(ContextCompat.getColor(this, android.R.color.white)))
                .append(wakieItem.getTime().toString(":mm"), lightSpan, new ForegroundColorSpan(ContextCompat.getColor(this, android.R.color.white)));

        time.setText(alarmText);
        editTime.setText(wakieItem.getTime().toString("H:mm aa"));
    }
}
