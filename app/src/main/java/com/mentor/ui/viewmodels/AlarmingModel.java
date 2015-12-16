package com.mentor.ui.viewmodels;

import org.parceler.Parcel;

/**
 * Created by Joel on 16/12/2015.
 */
@Parcel
public class AlarmingModel {

    String alarmId;
    String wakerFacebookId;
    String wakerName;
    String message;
    boolean vibrate;

    public String getWakerFacebookId() {
        return wakerFacebookId;
    }

    public void setWakerFacebookId(String wakerFacebookId) {
        this.wakerFacebookId = wakerFacebookId;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getWakerName() {
        return wakerName;
    }

    public void setWakerName(String wakerName) {
        this.wakerName = wakerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }
}
