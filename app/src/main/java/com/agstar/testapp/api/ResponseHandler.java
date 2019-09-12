package com.agstar.testapp.api;

import android.content.Context;

import com.agstar.testapp.api.event.ApiResponseMessageEvent;
import com.agstar.testapp.api.model.response.ActivityResponseModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Response Handler used to process all messages/json in application.
 */
public class ResponseHandler {

    private static final String TAG = "Response Handler";
    private static ResponseHandler responseHandler;

    private Context mContext;

    private ResponseHandler() {

    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static ResponseHandler getInstance(Context context) {
        if (responseHandler == null) {
            responseHandler = new ResponseHandler();
        }
        responseHandler.mContext = context;
        return responseHandler;
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(ApiResponseMessageEvent.LoadActivities activities) {
        ActivityResponseModel responseModel = activities.getResponseModel();
        //Process data & store to database if not required on UI immediately

    }


}