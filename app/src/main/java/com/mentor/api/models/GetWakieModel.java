package com.mentor.api.models;

import org.joda.time.DateTime;

/**
 * Created by Joel on 14/12/2015.
 */
public class GetWakieModel {
    private DateTime time;
    private String wakieId;
    private String mentorId;
    private Boolean vibrate;
    private String mentorName;

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public String getWakieId() {
        return wakieId;
    }

    public void setWakieId(String wakieId) {
        this.wakieId = wakieId;
    }

    public String getMentorId() {
        return mentorId;
    }

    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    public Boolean getVibrate() {
        return vibrate;
    }

    public void setVibrate(Boolean vibrate) {
        this.vibrate = vibrate;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }
}
