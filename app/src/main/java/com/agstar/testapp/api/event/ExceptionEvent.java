package com.agstar.testapp.api.event;


public class ExceptionEvent {


    public static class NetworkException {
        String message;

        public NetworkException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

    public static class ApiException {
        String message;

        public ApiException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }


}