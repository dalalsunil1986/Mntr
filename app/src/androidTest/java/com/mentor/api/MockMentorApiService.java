package com.mentor.api;

import com.mentor.api.MentorApiService;
import com.mentor.api.models.CreateWakieModel;
import com.mentor.api.models.GetWakieModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Query;

/**
 * Created by Joel on 23/11/2015.
 */
public class MockMentorApiService implements MentorApiService{

    @Override
    public Call<GetWakieModel> createWakie(@Body CreateWakieModel wakieModel) {
        return null;
    }

    @Override
    public Call<List<GetWakieModel>> getWakies(@Query("page") int page, @Query("pageSize") int pageSize, @Query("deviceId") String deviceId) {
        return null;
    }
}
