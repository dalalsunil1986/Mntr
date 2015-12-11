package com.mentor.api.models;

import org.joda.time.DateTime;

/**
 * Created by Joel on 10/12/2015.
 */
public class CreateWakieModel
{
    private DateTime Time;
    private String wakieId;
    private String mentorId;
    private Boolean vibrate;

    public DateTime getTime() {
        return Time;
    }

    public void setTime(DateTime time) {
        Time = time;
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
}
