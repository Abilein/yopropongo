package com.xiberty.propongo.accounts;

import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.Excepts;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.OAuthCollection;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.credentials.CredentialService;
import com.xiberty.propongo.db.AppDatabase;
import com.xiberty.propongo.db.Commission;

import java.util.List;

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
