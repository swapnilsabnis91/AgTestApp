package com.agstar.testapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

public class Prefs {
    private static final String KEY_USER_ID = "key_customer_id";
    private static final String KEY_ACCESS_TOKEN = "key_access_token";
    private static final String KEY_USER_IS_LOGGED_IN = "key_is_logged_in";
    private static SharedPreferences _prefs;
    private static Prefs _instance;
    private static Context mContext;
    Gson gson = new Gson();

    public static void init(Context c) {
        _prefs = PreferenceManager.getDefaultSharedPreferences(c);
        mContext = c;
    }

    public static Prefs getInstance() {

        if (_instance == null) {
            _instance = new Prefs();
        }

        return _instance;
    }


    public AccessToken getAccessToken() {
        return gson.fromJson(_prefs.getString(KEY_ACCESS_TOKEN, ""), AccessToken.class);
    }

    public void setAccessToken(AccessToken accessToken) {
        _prefs.edit().putString(KEY_ACCESS_TOKEN, gson.toJson(accessToken)).apply();
    }

    public TwitterSession getTwitterAccessToken() {
        return gson.fromJson(_prefs.getString(KEY_ACCESS_TOKEN, ""), TwitterSession.class);
    }

    public void setTwitterAccessToken(Result<TwitterSession> accessToken) {
        _prefs.edit().putString(KEY_ACCESS_TOKEN, gson.toJson(accessToken)).apply();
    }

    public String getUserId() {
        return _prefs.getString(KEY_USER_ID, "");
    }


//    public void clearPreferencesHack() {
//        try {
//            _prefs.edit()
//                    .putString(KEY_ACCESS_TOKEN, "")
//                    .putBoolean(KEY_USER_IS_LOGGED_IN, false)
//                    .apply();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


//    public void setUserPreferences(String userId, String name,
//                                   String email, String profileUrl, String accessToken, String mobile, long expires) {
//        setAccessToken(accessToken);
//        if (!userId.equals(getUserId()))
//            _prefs.edit()
//                    .putString(KEY_USER_ID, userId)
//                    .apply();
//
//
//    }
//
//    public void setUserPreferences(String userId, String name,
//                                   String email, String profileUrl, String mobile) {
//        _prefs.edit()
//                .putString(KEY_USER_ID, userId)
//                .apply();
//
//
//    }

    public void setUserId(String userId) {
        _prefs.edit().putString(KEY_USER_ID, userId).apply();
    }

    public boolean getKeyUserIsLoggedIn() {
        return _prefs.getBoolean(KEY_USER_IS_LOGGED_IN, false);
    }

    public void setKeyUserIsLoggedIn(boolean isLoggedIn) {
        _prefs.edit().putBoolean(KEY_USER_IS_LOGGED_IN, isLoggedIn).apply();
    }


}
