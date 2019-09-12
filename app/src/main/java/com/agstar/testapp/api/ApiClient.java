package com.agstar.testapp.api;


import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final ApiClient ourInstance = new ApiClient();
    private volatile static Retrofit retrofit;

    static ApiClient getInstance() {
        return ourInstance;
    }

    public ActoApiInterface getAPIService() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(10, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(false);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request.Builder builder = original.newBuilder();
            builder.header(Config.AUTH_HEADER, "auth-token-from-preference");
            builder.method(original.method(), original.body());
            Request request = builder.build();

            Response response = chain.proceed(request);
            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                RequestHandler.getInstance().forceLogout();
                return response;
            }
            return response;
        });


        httpClient.addInterceptor(logging);  // <-- this is the important line!

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_HOME)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ActoApiInterface.class);
    }
}
