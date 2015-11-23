package com.mentor.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mentor.MentorApp;
import com.mentor.R;
import com.mentor.api.MentorTokenService;
import com.mentor.core.PreferenceManager;
import com.mentor.injection.component.ApplicationComponent;
import com.mentor.util.SnackBarFactory;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements FacebookCallback {

    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;
    private MaterialDialog authDialog;

    @Inject
    MentorTokenService mentorTokenService;
    @Inject
    PreferenceManager preferenceManager;
    private CallbackManager callbackManager;
    private List<String> permissionsNeeded = Arrays.asList("public_profile", "email", "user_friends");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        applicationComponent().inject(this);

        authDialog = new MaterialDialog.Builder(this)
                .title(R.string.authenticating)
                .content(R.string.please_wait)
                .cancelable(false)
                .progress(true, 0).build();

    }

    @OnClick(R.id.facebook_login)
    public void facebookLogin() {
        if (SnackBarFactory.checkConnectivitySnackbar(this,coordinator)==null)
        {
            LoginManager.getInstance().logInWithReadPermissions(this, permissionsNeeded);
        }
        else
        {
            SnackBarFactory.checkConnectivitySnackbar(this,coordinator).show();
        }
    }

    @Override
    public void onSuccess(Object o) {
        final LoginResult loginResult = (LoginResult) o;

        GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                if(response.getError()!=null)
                {
                    SnackBarFactory.createSnackbar(LoginActivity.this,coordinator,R.string.something_wrong);
                }
                else
                {


                }
            }
        });


    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    protected ApplicationComponent applicationComponent() {
        return MentorApp.get(this).getComponent();
    }
}
