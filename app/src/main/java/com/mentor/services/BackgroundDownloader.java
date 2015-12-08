package com.mentor.services;

/**
 * Created by Joel on 08/12/2015.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class BackgroundDownloader extends AsyncTask<String, Void, Void> {

    private static String TAG = "BackgroundDownloader";

    /**
     * Downloading file in background thread
     */
    @Override
    protected Void doInBackground(String... params) {
        int count;
        try {
            URL url = new URL(params[0].replace(" ", "%20"));
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            InputStream input = new BufferedInputStream(url.openStream(),
                    8192);
            OutputStream output = new FileOutputStream(params[1]);

            byte data[] = new byte[1024];

            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.flush();

            output.close();
            input.close();

            Log.v(TAG, "Completed download.");

        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

        return null;
    }
}