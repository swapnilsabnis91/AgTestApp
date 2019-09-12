package com.agstar.testapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.agstar.testapp.R;
import com.agstar.testapp.utils.Prefs;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends BaseActivity {
    private static final int RC_SIGN_IN = 30;
    String TAG = LoginActivity.class.getSimpleName();
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private GoogleSignInClient mGoogleSignInClient;
    private TwitterLoginButton twitterLoginButton;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestScopes(new Scope(Scopes.PLUS_ME), new Scope(Scopes.PROFILE))
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        init();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Log.e(TAG, "Login Result" + loginResult.toString());

                Prefs.getInstance().setAccessToken(loginResult.getAccessToken());
                startActivity(new Intent(getApplicationContext(), AgFacebookActivity.class));

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(LoginActivity.this, "error to Login Facebook", Toast.LENGTH_SHORT).show();

            }
        });


        findViewById(R.id.sign_in_button).setOnClickListener(v -> signIn());

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.e(TAG, "Twitter Result" + result.toString());
                Prefs.getInstance().setTwitterAccessToken(result);
                startActivity(new Intent(getApplicationContext(), AgTwitterActivity.class));
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else if (requestCode == 500) {
            mGoogleSignInClient.signOut();
        }
    }

    private void init() {
        mContext = getApplicationContext();
        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("user_birthday");
        permissions.add("user_posts");
        permissions.add("user_photos");
        permissions.add("public_profile");
        permissions.add("user_friends");
        permissions.add("user_gender");

        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setPermissions(permissions);
        twitterLoginButton = findViewById(R.id.twitterLoginButton);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(final GoogleSignInAccount account) {
        Log.e(TAG, "My Google Account Details : " + account);
        if (account != null) {
            Intent googleIntent = new Intent(getApplicationContext(), AgGooglePlusActivity.class);
            googleIntent.putExtra("google_data", gson.toJson(account));
            startActivityForResult(googleIntent, 500);
        }
    }
}
