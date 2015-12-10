package com.mentor.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by Joel on 08/12/2015.
 */
public class GeneralUtils {
    public static String facebookPicture(String id)
    {
        return "https://graph.facebook.com/" + id+ "/picture?width=200&height=200";
    }

    public static Bitmap getBitmapFromFile(File file)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }


}
