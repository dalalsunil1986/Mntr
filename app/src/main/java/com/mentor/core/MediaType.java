package com.mentor.core;

/**
 * Created by Joel on 08/12/2015.
 */
public enum MediaType {
    ProfilePic ("profile");

    private final String name;

    private MediaType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}