package com.agstar.testapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.agstar.testapp.R;


public class SplashScreenActivity extends BaseActivity {

    CountDownTimer mCountDownTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mCountDownTimer = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                openMainActivity();
            }
        };
        mCountDownTimer.start();


    }


    private void openMainActivity() {
        Intent intent;
        intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


}
