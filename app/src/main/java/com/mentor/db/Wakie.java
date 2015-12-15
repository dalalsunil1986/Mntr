package com.mentor.db;

import com.orm.SugarRecord;

import java.util.Date;


/**
 * Created by Joel on 12/12/2015.
 */
public class Wakie extends SugarRecord {
    private Date time;
    private String mentorName;
    private String mentorId;
    private String wakieId;
    private int wakieIdentifier;

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

    public String getWakieId() {
        return wakieId;
    }

    public void setWakieId(String wakieId) {
        this.wakieId = wakieId;
    }

    public int getWakieIdentifier() {
        return wakieIdentifier;
    }

    public void setWakieIdentifier(int wakieIdentifier) {
        this.wakieIdentifier = wakieIdentifier;
    }
}
