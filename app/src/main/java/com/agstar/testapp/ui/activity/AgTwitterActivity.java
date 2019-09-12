package com.agstar.testapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.agstar.testapp.R;
import com.agstar.testapp.ui.fragment.TimelineFragment;
import com.agstar.testapp.utils.CircleTransform;
import com.agstar.testapp.utils.Prefs;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

public class AgTwitterActivity extends BaseActivity {
    String TAG = AgTwitterActivity.class.getSimpleName();
    ImageView imgProfile;
    TextView tvName, tvEmail, tvGender, tvBirthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_twitter);

        init();


        if (Prefs.getInstance().getTwitterAccessToken() != null && !Prefs.getInstance().getTwitterAccessToken().equals("")) {

            login(Prefs.getInstance().getTwitterAccessToken());
        }

        setFragment(new TimelineFragment(), R.id.frame_container);
    }

    public void login(TwitterSession session) {

        //Creating a twitter session with result's data
        //  TwitterSession session = result.data;

        //Getting the username from session
        final String username = session.getUserName();

        //This code will fetch the profile image URL
        //Getting the account service of the user logged in
        TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false, true).enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                String name = userResult.data.name;
                String email = userResult.data.email;

                // _normal (48x48px) | _bigger (73x73px) | _mini (24x24px)
                String photoUrlNormalSize = userResult.data.profileImageUrl;
                String photoUrlBiggerSize = userResult.data.profileImageUrl.replace("_normal", "_bigger");
                String photoUrlMiniSize = userResult.data.profileImageUrl.replace("_normal", "_mini");
                String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");
                updateMyProfileUi(name, email, photoUrlBiggerSize);
            }

            @Override
            public void failure(TwitterException exc) {
                Log.d("TwitterKit", "Verify Credentials Failure", exc);
            }
        });


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

    private void updateMyProfileUi(String name, String email, String photoUrlBiggerSize) {
        Picasso.get()
                .load(photoUrlBiggerSize).transform(new CircleTransform())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imgProfile);

        tvName.setText(name);
        tvEmail.setText(email);
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
