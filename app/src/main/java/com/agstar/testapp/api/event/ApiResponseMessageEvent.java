package com.agstar.testapp.api.event;


import com.agstar.testapp.api.model.response.ActivityLikesResponseModel;
import com.agstar.testapp.api.model.response.ActivityResponseModel;

public class ApiResponseMessageEvent {


    public static class LoadActivities {
        ActivityResponseModel responseModel;

        public LoadActivities(ActivityResponseModel responseModel) {
            this.responseModel = responseModel;
        }

        public ActivityResponseModel getResponseModel() {
            return responseModel;
        }
    }


    public static class LoadActivityLikes {
        ActivityLikesResponseModel responseModel;

        public LoadActivityLikes(ActivityLikesResponseModel responseModel) {
            this.responseModel = responseModel;
        }

        public ActivityLikesResponseModel getResponseModel() {
            return responseModel;
        }
    }
}
