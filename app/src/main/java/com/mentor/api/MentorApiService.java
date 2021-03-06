package com.mentor.api;

import com.mentor.api.models.CreateWakieModel;
import com.mentor.api.models.GetWakieModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Joel on 11/11/2015.
 */
public interface  MentorApiService {
    String ENDPOINT ="http://getmentorapp.azurewebsites.net/api/";
    String TEST_ENDPOINT="http://getmentorapp.azurewebsites.net/api/";

    @POST("WakieWakie/Create")
    Call<GetWakieModel> createWakie(@Body CreateWakieModel wakieModel);

    @GET("WakieWakie/GetAlarms")
    Call<List<GetWakieModel>> getWakies(@Query("page") int page,
                                        @Query("pageSize") int pageSize,
                                        @Query("deviceId")String deviceId);

}
