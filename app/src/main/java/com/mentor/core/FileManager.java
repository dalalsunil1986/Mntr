package com.mentor.core;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Joel on 08/12/2015.
 */
public class FileManager
{
    public static File get(String id,Context context,MediaType mediaType,Extension extension)
    {
        File file =new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/media/"+mediaType+"/"+id+extension);


        File folder =new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/media/"+mediaType);

        File noMedia = new File(folder.getAbsolutePath() + "/.nomedia");


        if (! file.exists()){
            return null;
        }
        return file;
    }

    /*
    *Used to create a new file. If the file already exists it's deleted.
    * */
    public static File create(String id, Context context, MediaType mediaType,Extension extension)
    {
        File file =new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/media/"+mediaType+"/"+id+extension);

        File folder =new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/media/"+mediaType);

        File noMedia = new File(folder.getAbsolutePath() + "/.nomedia");


        if (! folder.exists()){
            if (! folder.mkdirs()){
                return null;
            }
        }


        if(file.exists())
        {
            file.delete();
        }
        return file;
    }
}
