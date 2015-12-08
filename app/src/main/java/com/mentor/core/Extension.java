package com.mentor.core;

/**
 * Created by Joel on 08/12/2015.
 */
public enum Extension
{
    PNG (".png"),
    JPEG(".jpg");

    private final String name;

    private Extension(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}