
package com.xiberty.propongo.credentials;

import android.content.Context;

import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.AccountService;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.Excepts;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.councils.models.ProposalResponse;
import com.xiberty.propongo.credentials.responses.OAuthCredential;
import com.xiberty.propongo.credentials.responses.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter implements LoginContract.Presenter {



    private LoginContract.View mView;
    private CredentialService cService;

    public LoginPresenter(LoginContract.View mView, CredentialService cService) {
        this.mView = mView;
        this.cService = cService;
    }


    @Override
    public void onCreate() {    }


    @Override
    public void onDestroy() {
        mView = null;
    }



    @Override
    public void login(final Context context, String user, String password) {
        if (mView != null) {

            mView.showProgress();

            Call<OAuthCredential> responseCall = cService.getAccessToken(Constants.CLIENT_ID, Constants.CLIENT_SECRET, user, password);
            responseCall.enqueue(new Callback<OAuthCredential>() {
                @Override
                public void onResponse(Call<OAuthCredential> call, Response<OAuthCredential> response) {

                    mView.hideProgress();
                    if (response.isSuccessful()) {

                        OAuthCredential credentials = response.body();
                        Store.saveCredential(context, credentials);
                        mView.signInSuccess(credentials);

                    } else {
                        if(response.code()>=500 && response.code() <= 550) {
                            mView.signInError(context.getString(R.string.error_server));
                        } else {
                            FormattedResp error = ParserError.parse(response);
                            String errorMessage = MessageManager.getMessage(context, error.code());
                            mView.signInError(errorMessage);
                        }
                    }
                }

                @Override
                public void onFailure(Call<OAuthCredential> call, Throwable t) {

                    mView.hideProgress();

                    if(Excepts.isConnectionFailure(t)) {
                        String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                        mView.signInError(errorMessage);
                    }
                }
            });
        }

    }

    @Override
    public void getProfile(final Context context, AccountService aService) {


        Call<UserProfile> request = aService.getProfile();
        request.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                if (response.isSuccessful()){
                    UserProfile profile = response.body();
                    Store.saveProfile(context, profile);
                    mView.getProfileSuccess();
                } else {
                    if(response.code()>=500 && response.code() <= 550) {
                        mView.signInError(context.getString(R.string.error_server));
                    } else {
                        FormattedResp error = ParserError.parse(response);
                        String errorMessage = MessageManager.getMessage(context, error.code());
                        mView.getProfileError(errorMessage);
                    }
                }
            }
            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                if(Excepts.isConnectionFailure(t)) {
                    String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                    mView.getProfileError(errorMessage);
                }
            }
        });
    }


    @Override
    public void facebookLogin(final Context context, String facebookToken) {
        Call<OAuthCredential> responseCall = cService.facebookLogin(
                    Constants.CLIENT_ID, Constants.CLIENT_SECRET, facebookToken);

        responseCall.enqueue(new Callback<OAuthCredential>() {
            @Override
            public void onResponse(Call<OAuthCredential> call, Response<OAuthCredential> response) {
                if (response.isSuccessful()){
                    OAuthCredential credential = response.body();
                    Store.saveCredential(context, credential);
                    mView.signInSuccess(credential);
                } else {
                    if(response.code()>=500 && response.code() <= 550) {
                        mView.signInError(context.getString(R.string.error_server));
                    } else {
                        FormattedResp error = ParserError.parse(response);
                        String errorMessage = MessageManager.getMessage(context, error.code());
                        mView.signInError(errorMessage);
                    }
                }
            }
            @Override
            public void onFailure(Call<OAuthCredential> call, Throwable t) {

                if(Excepts.isConnectionFailure(t)) {
                    String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                    mView.signInError(errorMessage);
                }

            }
        });
    }


}
