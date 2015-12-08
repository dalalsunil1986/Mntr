package com.mentor.util;

/**
 * Created by Joel on 08/12/2015.
 */
public class GeneralUtils {
    public static String facebookPicture(String id)
    {
        return "https://graph.facebook.com/" + id+ "/picture?width=200&height=200";
    }
}
