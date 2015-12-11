package com.mentor.ui.viewmodels;

import org.joda.time.DateTime;
import org.parceler.Parcel;

/**
 * Created by Joel on 11/12/2015.
 */

@Parcel
public class WakieItem {
    DateTime time;
    String mentorName;
    String mentorId;
    boolean vibrate=false;
    String wakieId;
    DateTime date;

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorId() {
        return mentorId;
    }

    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    public String getWakieId() {
        return wakieId;
    }

    public void setWakieId(String wakieId) {
        this.wakieId = wakieId;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
