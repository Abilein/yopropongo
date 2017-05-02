package com.xiberty.propongo.onset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiberty.propongo.R;
import com.xiberty.propongo.credentials.LoginActivity;
import com.xiberty.propongo.credentials.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

import android.view.View;

public class StartActivity extends AppCompatActivity implements StartContract.View {
    StartPresenter startPresenter;


    @BindView(R.id.btnSignIn) FancyButton btnSignIn;
    @BindView(R.id.btnSignUp) FancyButton btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ButterKnife.bind(this);
        startPresenter = new StartPresenter(this);
        startPresenter.isLoggedIn(this);

        // TODO Crear la lógica
        //        AppSyncAdapter.initializeSyncAdapter(getBaseContext());
    }

    @OnClick(R.id.btnSignUp)
    public void goToRegister(View view) {
        Intent intent = new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnSignIn)
    public void goToLogin(View view) {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
