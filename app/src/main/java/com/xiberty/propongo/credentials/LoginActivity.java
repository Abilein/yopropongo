
package com.xiberty.propongo.credentials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.login.LoginManager;
import com.xiberty.propongo.R;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.xiberty.propongo.accounts.AccountService;
import com.xiberty.propongo.accounts.MainActivity;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.views.XEditText;
import com.xiberty.propongo.credentials.responses.OAuthCredential;

import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.mateware.snacky.Snacky;
import mehdi.sakout.fancybuttons.FancyButton;

public class LoginActivity extends AppCompatActivity implements LoginContract.View, FacebookCallback<LoginResult> {



    LoginContract.Presenter loginPresenter;
    CallbackManager callbackManager;

    @BindView(R.id.btnEnter) FancyButton btnEnter;
    @BindView(R.id.txtUser) XEditText txtUser;
    @BindView(R.id.txtPassword) XEditText txtPassword;
    @BindView(R.id.lblForgotPassword) TextView lblForgotPassword;
    @BindView(R.id.checkRememberPass) CheckBox checkRememberPass;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.layoutMainContainer) RelativeLayout container;
    @BindView(R.id.goRegister) TextView goRegister;


    @BindView(R.id.btnFacebookLogin) FancyButton btnFacebookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        CredentialService credentialService = WS.makeService(CredentialService.class);
        loginPresenter = new LoginPresenter(this, credentialService);

        setupFacebookButton();
    }

    public void setupFacebookButton(){
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
    }


    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void showProgress() { progressBar.setVisibility(android.view.View.VISIBLE); }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(android.view.View.GONE);
    }


    @Override
    @OnClick(R.id.btnEnter)
    public void signInHandler() {
        loginPresenter.login(this, txtUser.getText().toString(), txtPassword.getText().toString());
    }

    @Override
    @OnClick(R.id.lblForgotPassword)
    public void forgotPasswordHandler() { startActivity(new Intent(this, ResetPassActivity.class)); }

    @Override
    public void getProfileSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void getProfileError(String message) { signInError(message); }

    @Override
    public void signInSuccess(OAuthCredential credential) {
        showProgress();
        AccountService aService = WS.makeService(AccountService.class, credential);
        loginPresenter.getProfile(this, aService);
    }

    @Override
    @OnClick(R.id.btnFacebookLogin)
    public void facebookSignHandler() {
        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("public_profile");
        LoginManager.getInstance().logInWithReadPermissions(this, permissions);
    }

    @Override
    public void signInError(String message) {
        Snacky.builder()
                .setActivty(LoginActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSuccess(LoginResult loginResult) {
        showProgress();
        loginPresenter.facebookLogin(this, AccessToken.getCurrentAccessToken().getToken());
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        signInError(error.getMessage());
    }


    @OnClick(R.id.goRegister)
    public void goRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
