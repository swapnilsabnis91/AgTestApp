package com.agstar.testapp.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActivityLikesResponseModel extends BaseResponseModel implements Serializable {

    @SerializedName("body")
    @Expose
    private Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }


    public class Body extends Post {

    }
}