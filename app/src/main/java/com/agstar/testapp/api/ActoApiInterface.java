package com.agstar.testapp.api;

import com.agstar.testapp.api.event.ApiCallMessageEvent;
import com.agstar.testapp.api.model.response.ActivityLikesResponseModel;
import com.agstar.testapp.api.model.response.ActivityResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ActoApiInterface {

    @GET("myactivities/all")
    Call<ActivityResponseModel> getActivity();

    @POST("myactivities/likes")
    Call<ActivityLikesResponseModel> updateLikes(@Body ApiCallMessageEvent.UpdateLikes updateLikes);
}
