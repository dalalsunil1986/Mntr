package com.mentor.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

/**
 * Created by Joel on 17/11/2015.
 */
public class ViewUtils {
    public static Drawable getMaterialIcon(Context context,IIcon icon,int size)
    {
        return new IconicsDrawable(context,icon).colorRes(android.R.color.white).sizeDp(size);
    }

    public static Drawable getMaterialIcon(Context context,IIcon icon)
    {
        return getMaterialIcon(context,icon,18);
    }
}
