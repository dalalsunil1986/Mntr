package com.mentor.api;

import com.mentor.api.models.CreateWakieModel;

import retrofit.Call;
import retrofit.http.Body;

/**
 * Created by Joel on 11/11/2015.
 */
public interface  MentorApiService {
    String ENDPOINT ="http://getmentorapp.azurewebsites.net/api/";
    String TEST_ENDPOINT="http://getmentorapp.azurewebsites.net/api/";

    Call<CreateWakieModel> createWakie(@Body CreateWakieModel wakieModel);

}
