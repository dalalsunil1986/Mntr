package com.mentor.db;

import org.joda.time.DateTime;

/**
 * Created by Joel on 24/11/2015.
 */
public class SearchLog {
    private String query;
    private DateTime date;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
