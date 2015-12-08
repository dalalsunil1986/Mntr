package com.mentor.services;

import android.app.IntentService;
import android.content.Intent;

import com.mentor.core.Extension;
import com.mentor.core.FileManager;
import com.mentor.core.MediaType;
import com.mentor.core.WakefulIntentService;
import com.mentor.util.Constants;
import com.mentor.util.GeneralUtils;

import java.io.File;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class FbProfilePhotoService extends WakefulIntentService {
    private String facebookId;


    public FbProfilePhotoService() {
        super("FbProfilePhotoService");
    }

    @Override
    protected void doWakefulWork(Intent intent) {
        facebookId = intent.getStringExtra("id");

        File file = FileManager.create(Constants.PROFILE,this, MediaType.ProfilePic, Extension.JPEG);

        if(file!=null)
        {
            new BackgroundDownloader().execute(GeneralUtils.facebookPicture(facebookId),
                    file.getAbsolutePath());
        }
    }
}