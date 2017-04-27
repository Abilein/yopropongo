package com.xiberty.warpp.credentials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.xiberty.warpp.R;
import com.xiberty.warpp.accounts.MainActivity;
import com.xiberty.warpp.contrib.api.WS;
import com.xiberty.warpp.contrib.views.XEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.mateware.snacky.Snacky;
import mehdi.sakout.fancybuttons.FancyButton;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {


    private RegisterPresenter presenter;

    @NotEmpty(messageResId=R.string.validation_required)
    @BindView(R.id.txtUsername)
    XEditText txtUsername;

    @NotEmpty(messageResId=R.string.validation_required)
    @Email(messageResId=R.string.validation_email)
    @BindView(R.id.txtEmail)
    XEditText txtEmail;

    @NotEmpty(messageResId=R.string.validation_required)
    @BindView(R.id.txtPassword)
    XEditText txtPassword;


    @BindView(R.id.btnRegister) FancyButton btnRegister;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.goLogin) TextView goLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        CredentialService credentialService = WS.makeService(CredentialService.class);
        presenter = new RegisterPresenter(this, credentialService);
    }


    @Override
    @OnClick(R.id.btnRegister)
    public void signupHandler() {
        Validator validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                presenter.register(
                        RegisterActivity.this,
                        txtUsername.getText().toString(),
                        txtEmail.getText().toString(),
                        txtPassword.getText().toString());
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(RegisterActivity.this);
                    if (view instanceof XEditText) {
                        ((XEditText) view).setError(message);
                    } else {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        validator.validate();
    }

    @Override
    public void signupError(String message) {
        Snacky.builder()
                .setActivty(RegisterActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
    }

    @Override
    public void signupSuccess(String message, String username, String password) {
        Snacky.builder()
                .setActivty(RegisterActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_LONG)
                .success()
                .show();
        presenter.getCredentials(this, username, password);
    }

    @Override
    public void getCredentialError(String message) {
        signupError(message);
    }

    @Override
    public void getCredentialSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(android.view.View.GONE);
    }


    @OnClick(R.id.goLogin)
    public void goLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


}
