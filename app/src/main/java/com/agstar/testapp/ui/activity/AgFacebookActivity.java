package com.agstar.testapp.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.agstar.testapp.R;
import com.agstar.testapp.model.fb.FbUserModel;
import com.agstar.testapp.ui.fragment.TimelineFragment;
import com.agstar.testapp.utils.CircleTransform;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

public class AgFacebookActivity extends BaseActivity {
    String TAG = AgFacebookActivity.class.getSimpleName();
    ImageView imgProfile;
    TextView tvName, tvEmail, tvGender, tvBirthday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_facebook);

        init();
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/photos",
                null,
                HttpMethod.GET,
                response -> {
                    /* handle the result */
                    Log.e(TAG, "Basics Photos" + response.getRawResponse());
                }
        ).executeAsync();

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                (object, response) -> {
                    Log.e(TAG, "Basics" + response.getRawResponse());
                    FbUserModel fbUserModel = gson.fromJson(response.getRawResponse(), FbUserModel.class);

                    updateMyProfileUi(fbUserModel);


                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,gender,birthday,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

        setFragment(new TimelineFragment(),R.id.frame_container);
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
                LoginManager.getInstance().logOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateMyProfileUi(FbUserModel fbUserModel) {
        Picasso.get()
                .load(fbUserModel.getPicture().getData().getUrl()).transform(new CircleTransform())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(imgProfile);

        tvName.setText(fbUserModel.getName());
        tvEmail.setText(fbUserModel.getEmail());
        tvGender.setText(fbUserModel.getGender());
        tvBirthday.setText(fbUserModel.getBirthday());
    }

    private void init() {
        imgProfile = findViewById(R.id.img_profile);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvGender = findViewById(R.id.tv_gender);
        tvBirthday = findViewById(R.id.tv_birthday);
    }
}
