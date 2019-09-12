package com.agstar.testapp.ui.activity;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.agstar.testapp.api.event.ExceptionEvent;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseActivity extends AppCompatActivity {
    public Gson gson = new Gson();
    private String TAG = "Base Activity";

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ExceptionEvent.NetworkException e) {
        Log.e(TAG, "Error : " + e.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ExceptionEvent.ApiException e) {
        Log.e(TAG, "Error : " + e.toString());
    }

    protected void setFragment(Fragment fragment, int frame_container) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(frame_container, fragment);
        fragmentTransaction.commit();
    }
}
