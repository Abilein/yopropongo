package com.xiberty.warpp.accounts;

import android.content.Context;

import com.xiberty.warpp.Constants;
import com.xiberty.warpp.contrib.Store;
import com.xiberty.warpp.contrib.api.Excepts;
import com.xiberty.warpp.contrib.api.FormattedResp;
import com.xiberty.warpp.contrib.api.MessageManager;
import com.xiberty.warpp.contrib.api.OAuthCollection;
import com.xiberty.warpp.contrib.api.ParserError;
import com.xiberty.warpp.credentials.CredentialService;
import com.xiberty.warpp.credentials.responses.UserProfile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private AccountService aService;
    private CredentialService cService;

    public MainPresenter(MainContract.View mView, AccountService aService, CredentialService cService) {
        this.mView = mView;
        this.aService = aService;
        this.cService = cService;
    }

    @Override
    public void logout(final Context context) {
        if(Store.isLoggedIn(context)) {


            Call<ResponseBody> tokenResponseCall = cService.logout(
                    Constants.CLIENT_ID, Constants.CLIENT_SECRET, Store.getAccessToken(context));

            tokenResponseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    mView.hideProgress();
                    if (response.isSuccessful()) {
                        Store.removeCredential(context);
                        mView.logoutSuccess();
                    } else {
                        FormattedResp error = ParserError.parse(response);
                        String errorMessage = MessageManager.getMessage(context, error.code());
                        mView.logoutError(errorMessage);
                        if(error.code().equals(MessageManager.INVALID_ACCESS_TOKEN)) {
                            Store.removeCredential(context);
                            mView.logoutSuccess();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mView.hideProgress();

                    if(Excepts.isConnectionFailure(t)) {

                        // TODO Construir un servicio que elimine los AcessToken PASADOS

                        OAuthCollection oauthCollection = Store.getOAuthCollection(context);
                        oauthCollection.credentials().add(Store.getAccessToken(context));
                        Store.saveOAuthCollection(context, oauthCollection);

                        String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                        Store.removeCredential(context);
                        mView.logoutError(errorMessage);
                        mView.logoutSuccess();

                    }

                    // TODO VALIDAR CUANDO EN EL SERVER SALE UN ERROR 500, 403
                }
            });

        }
    }
}
