package com.mentor.api;

import com.mentor.api.models.BearerToken;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Joel on 11/11/2015.
 */
public interface MentorTokenService {
    String ENDPOINT ="http://getmentorapp.azurewebsites.net";
    String TEST_ENDPOINT="http://getmentorapp.azurewebsites.net";

    @FormUrlEncoded
    @POST("/token") void fetchBearerToken(@Field("grant_type") String grantType, @Field("accessToken") String facebookAccessToken,@Field("deviceType")String deviceType,@Field("deviceId")String deviceId,@Field("pushId")String pushId,@Field("accountType")String accountType,Callback<BearerToken> bearerTokenCallback);
}
