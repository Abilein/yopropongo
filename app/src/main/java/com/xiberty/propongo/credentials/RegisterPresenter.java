package com.xiberty.propongo.credentials;

import android.content.Context;

import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.Excepts;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.credentials.responses.OAuthCredential;
import com.xiberty.propongo.credentials.responses.UserProfile;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;
    private CredentialService mService;

    public RegisterPresenter(RegisterContract.View mView, CredentialService service) {
        this.mView = mView;
        this.mService = service;
    }



    @Override
    public void register(final Context context, final String username, final String email, final String password) {


        Call<UserProfile> respuesta = mService.register(Constants.CLIENT_ID, Constants.CLIENT_SECRET,
                                            username, email, password);

        mView.showProgress();

        respuesta.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                mView.hideProgress();

                if (response.isSuccessful()) {


                    UserProfile profile = response.body();
                    Store.saveProfile(context, profile);

                    mView.signupSuccess(context.getString(R.string.success_account_created), username, password);

                } else {
                    FormattedResp error = ParserError.parse(response);


                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.signupError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                mView.hideProgress();

                if(Excepts.isConnectionFailure(t)) {
                    String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                    mView.signupError(errorMessage);
                }

                // TODO VALIDAR CUANDO EN EL SERVER SALE UN ERROR 500, 403
            }
        });
    }

    public void getCredentials(final Context context, final String username, final String password){

        Call<OAuthCredential> respuestaRegistro = mService.getAccessToken(Constants.CLIENT_ID,
                Constants.CLIENT_SECRET, username, password);

        respuestaRegistro.enqueue(new Callback<OAuthCredential>() {
            @Override
            public void onResponse(Call<OAuthCredential> call, Response<OAuthCredential> response) {
                if(response.code()==200 || response.code()==201){
                    OAuthCredential oAuthCredential = response.body();
                    Store.saveCredential(context, oAuthCredential);
                    mView.getCredentialSuccess();
                }
            }

            @Override
            public void onFailure(Call<OAuthCredential> call, Throwable t) {
                mView.hideProgress();

                if(Excepts.isConnectionFailure(t)) {
                    String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                    mView.getCredentialError(errorMessage);
                }

                // TODO VALIDAR CUANDO EN EL SERVER SALE UN ERROR 500, 403


            }
        });
    }
}
