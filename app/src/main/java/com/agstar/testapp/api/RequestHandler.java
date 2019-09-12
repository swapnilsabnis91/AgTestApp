package com.agstar.testapp.api;

import com.agstar.testapp.api.event.ApiCallMessageEvent;

import org.greenrobot.eventbus.EventBus;

public class RequestHandler {
    private static final RequestHandler ourInstance = new RequestHandler();

    private RequestHandler() {
    }


    public static RequestHandler getInstance() {
        return ourInstance;
    }


    public void getMyActivities() {
        EventBus.getDefault().post(new ApiCallMessageEvent.GetActivity());
    }

    public void updateLikeStatus(Long postId) {
        EventBus.getDefault().post(new ApiCallMessageEvent.UpdateLikes(postId));
    }

    public void forceLogout() {
    }
}
