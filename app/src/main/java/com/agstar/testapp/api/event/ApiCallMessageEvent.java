package com.agstar.testapp.api.event;


public class ApiCallMessageEvent {


    public static class GetActivity {

        public GetActivity() {
        }


    }


    public static class UpdateLikes {
        Long postId;

        public UpdateLikes(Long postId) {
            this.postId = postId;
        }

        public Long getPostId() {
            return postId;
        }
    }
}
