package com.mentor.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.style.ForegroundColorSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mentor.R;
import com.mentor.api.MentorApiService;
import com.mentor.api.models.CreateWakieModel;
import com.mentor.db.Wakie;
import com.mentor.ui.viewmodels.WakieItem;
import com.mentor.util.SnackBarFactory;
import com.mentor.util.Spanny;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class CreateWakieActivity extends BaseActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    WakieItem wakieItem;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.edit_time)
    TextView editTime;
    @Bind(R.id.edit_date)
    TextView editDate;
    @Bind(R.id.edit_mentor)
    TextView editMentor;

    DateTime now;
    @Bind(R.id.save)
    LinearLayout save;
    @Bind(R.id.delete)
    LinearLayout delete;
    @Bind(R.id.mentor_layout)
    RelativeLayout mentorLayout;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;

    @Inject
    Realm realm;

    @Inject
    MentorApiService mentorApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wakie);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        ButterKnife.bind(this);
        applicationComponent().inject(this);
        setToolbar(toolbar);
        now = DateTime.now();


        if (getIntent().hasExtra("wakie")) {
            wakieItem = Parcels.unwrap(getIntent().getParcelableExtra("wakie"));
            setUpWakie(wakieItem);

        } else {
            wakieItem = new WakieItem();
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
        editDate.setText(wakieItem.getDate().toString("MMMM d, yyyy"));
        editMentor.setText("Kristy Moreno");
    }

    @OnClick(R.id.time_layout)
    void timePicker() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, now.getHourOfDay(), now.getMinuteOfHour(), false);
        timePickerDialog.show();
    }

    @OnClick(R.id.date_layout)
    void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, now.getYear(), now.getMonthOfYear() - 1, now.getDayOfMonth());
        datePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("HH:mm");
        wakieItem.setTime(dateStringFormat.parseDateTime(hourOfDay + ":" + minute));

        setUpWakie(wakieItem);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        DateTimeFormatter dateStringFormat = DateTimeFormat.forPattern("MM/dd/yyyy");
        wakieItem.setDate(dateStringFormat.parseDateTime(monthOfYear + 1 + "/" + dayOfMonth + "/" + year));

        setUpWakie(wakieItem);
    }

    public void saveAlarm() {


        final DateTime alarmTime = new DateTime().withDate(wakieItem.getDate().toLocalDate()).withTime(wakieItem.getTime().toLocalTime());
        if (alarmTime.isBeforeNow()) {

            Snackbar snackbar = SnackBarFactory.createSnackbar(this, coordinator, R.string.time_passed);
            snackbar.show();
        } else {
            final MaterialDialog dialog = new MaterialDialog.Builder(this)
                    .title(R.string.wakie_saving)
                    .content(R.string.please_wait)
                    .cancelable(true)
                    .progress(true, 0).build();


            dialog.show();

            final RealmResults<Wakie> results = realm.where(Wakie.class).findAllAsync();
            results.addChangeListener(new RealmChangeListener() {
                @Override
                public void onChange() {

                    for (Wakie wakie : results) {
                        if (new DateTime(wakie.getTime()).isEqual(alarmTime)) {
                            SnackBarFactory.createSnackbar(CreateWakieActivity.this, coordinator, "This alarm already exists.").show();
                            dialog.dismiss();
                            return;
                        }
                    }
                }
            });

            CreateWakieModel createWakieModel = new CreateWakieModel();
            createWakieModel.setTime(alarmTime);
            createWakieModel.setMentorId(null);
            createWakieModel.setVibrate(true);

            Call<CreateWakieModel> wakieModelCall = mentorApiService.createWakie(createWakieModel);

            wakieModelCall.enqueue(new Callback<CreateWakieModel>() {
                @Override
                public void onResponse(Response<CreateWakieModel> response, Retrofit retrofit) {

                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

}


