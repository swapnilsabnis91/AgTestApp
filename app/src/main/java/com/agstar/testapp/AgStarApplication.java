package com.agstar.testapp;

import android.app.Application;
import android.util.Log;

import com.agstar.testapp.api.ApiService;
import com.agstar.testapp.api.ResponseHandler;
import com.agstar.testapp.utils.Prefs;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import org.greenrobot.eventbus.EventBus;

public class AgStarApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.init(this);
        EventBus.getDefault().register(new ApiService());
        EventBus.getDefault().register(ResponseHandler.getInstance(this));

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
            Stetho.initializeWithDefaults(this);
        }

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(
                        getResources().getString(R.string.Twitter_CONSUMER_KEY),
                        getResources().getString(R.string.Twitter_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
