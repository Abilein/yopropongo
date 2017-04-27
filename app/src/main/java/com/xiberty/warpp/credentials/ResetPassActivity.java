package com.xiberty.warpp.credentials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.xiberty.warpp.R;
import com.xiberty.warpp.contrib.api.WS;
import com.xiberty.warpp.contrib.views.XEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.mateware.snacky.Snacky;
import mehdi.sakout.fancybuttons.FancyButton;

public class ResetPassActivity extends AppCompatActivity implements ResetPassContract.View {

    @BindView(R.id.btnSend) FancyButton btnSend;
    @BindView(R.id.txtUser) XEditText txtUser;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;

    ResetPassContract.Presenter resetPassPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);
        CredentialService credentialService = WS.makeService(CredentialService.class);
        resetPassPresenter = new ResetPassPresenter(this, credentialService);

        setToolbar();
    }

    public void setToolbar(){
        toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_invisible));
        toolbar.setVisibility(android.view.View.VISIBLE);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.darken));
        setSupportActionBar(toolbar);
    }


    public void goToBack(android.view.View view) {
        finish();
    }


    @Override
    @OnClick(R.id.btnSend)
    public void sendHandler() {
        resetPassPresenter.resetPassword(this, txtUser.getText().toString());
    }

    @Override
    public void resetSuccess(String message) {
        Snacky.builder()
                .setActivty(ResetPassActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_LONG)
                .success()
                .show();
    }

    @Override
    public void resetError(String message) {
        Snacky.builder()
                .setActivty(ResetPassActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
    }

    @Override
    public void showProgress() { progressBar.setVisibility(View.VISIBLE); }

    @Override
    public void hideProgress() { progressBar.setVisibility(View.GONE); }
}
