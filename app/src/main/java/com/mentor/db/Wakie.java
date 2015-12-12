package com.mentor.db;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Joel on 12/12/2015.
 */
public class Wakie extends RealmObject {
    private Date time;
    private String mentorName;
    private String mentorId;
    private String alarmId;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }
}
