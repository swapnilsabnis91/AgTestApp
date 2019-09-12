package com.agstar.testapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.agstar.testapp.R;
import com.agstar.testapp.ui.fragment.TimelineFragment;
import com.agstar.testapp.utils.CircleTransform;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

public class AgGooglePlusActivity extends BaseActivity {
    String TAG = AgGooglePlusActivity.class.getSimpleName();
    ImageView imgProfile;
    TextView tvName, tvEmail, tvGender, tvBirthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_google_plus);

        init();

        if (getIntent() != null && getIntent().getExtras() != null) {
            GoogleSignInAccount account = gson.fromJson(getIntent().getStringExtra("google_data"), GoogleSignInAccount.class);
            updateMyProfileUi(account);
        }

        setFragment(new TimelineFragment(), R.id.frame_container);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ag_facebook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_logout:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("google_plus_logout", true);
                setResult(500, resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateMyProfileUi(GoogleSignInAccount account) {
        Picasso.get()
                .load(account.getPhotoUrl()).transform(new CircleTransform())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imgProfile);

        tvName.setText(account.getDisplayName());
        tvEmail.setText(account.getEmail());
//        tvGender.setText(account.getGender());
//        tvBirthday.setText(account.getBirthday());
    }

    private void init() {
        imgProfile = findViewById(R.id.img_profile);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvGender = findViewById(R.id.tv_gender);
        tvBirthday = findViewById(R.id.tv_birthday);
    }
}
