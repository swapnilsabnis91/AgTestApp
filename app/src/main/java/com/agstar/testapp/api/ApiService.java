package com.agstar.testapp.api;


import com.agstar.testapp.api.event.ApiCallMessageEvent;
import com.agstar.testapp.api.event.ApiResponseMessageEvent;
import com.agstar.testapp.api.event.ExceptionEvent;
import com.agstar.testapp.api.model.response.ActivityLikesResponseModel;
import com.agstar.testapp.api.model.response.ActivityResponseModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiService {

    ActoApiInterface getApi() {
        return ApiClient.getInstance().getAPIService();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(ApiCallMessageEvent.GetActivity getActivity) {

        Call<ActivityResponseModel> call = getApi().getActivity();

        call.enqueue(new Callback<ActivityResponseModel>() {
            @Override
            public void onResponse(Call<ActivityResponseModel> call, Response<ActivityResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == API.API_SUCCESS.ordinal()) {
                            EventBus.getDefault().post(new ApiResponseMessageEvent.LoadActivities(response.body()));
                        } else {
                            EventBus.getDefault().post(new ExceptionEvent.ApiException(response.body().getMessage()));
                        }

                    }

                } else {
                    EventBus.getDefault().post(new ExceptionEvent.ApiException(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ActivityResponseModel> call, Throwable t) {
                EventBus.getDefault().post(new ExceptionEvent.NetworkException(t.getMessage())
                );
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(ApiCallMessageEvent.UpdateLikes updateLikes) {

        Call<ActivityLikesResponseModel> call = getApi().updateLikes(updateLikes);

        call.enqueue(new Callback<ActivityLikesResponseModel>() {
            @Override
            public void onResponse(Call<ActivityLikesResponseModel> call, Response<ActivityLikesResponseModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == API.API_SUCCESS.ordinal()) {
                            EventBus.getDefault().post(new ApiResponseMessageEvent.LoadActivityLikes(response.body()));
                        } else {
                            EventBus.getDefault().post(new ExceptionEvent.ApiException(response.body().getMessage()));
                        }

                    }

                } else {
                    EventBus.getDefault().post(new ExceptionEvent.ApiException(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ActivityLikesResponseModel> call, Throwable t) {
                EventBus.getDefault().post(new ExceptionEvent.NetworkException(t.getMessage())
                );
            }
        });
    }

    enum API {API_FAILURE, API_SUCCESS}

}
