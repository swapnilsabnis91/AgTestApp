package com.agstar.testapp.api.model.response;


import com.google.gson.annotations.SerializedName;

public class BaseResponseModel {

    @SerializedName("status")
    int status;
    @SerializedName("message")
    String message;
    @SerializedName("errorCode")
    int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
