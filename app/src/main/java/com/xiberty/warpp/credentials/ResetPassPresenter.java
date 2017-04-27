package com.xiberty.warpp.credentials;

import android.content.Context;
import android.util.Log;

import com.xiberty.warpp.Constants;
import com.xiberty.warpp.R;
import com.xiberty.warpp.contrib.api.Excepts;
import com.xiberty.warpp.contrib.api.FormattedResp;
import com.xiberty.warpp.contrib.api.MessageManager;
import com.xiberty.warpp.contrib.api.ParserError;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetPassPresenter implements ResetPassContract.Presenter {

    private ResetPassContract.View mView;
    private CredentialService mService;

    public ResetPassPresenter(ResetPassContract.View mView, CredentialService service) {
        this.mView = mView;
        this.mService = service;
    }

    @Override
    public void onCreate() {    }


    @Override
    public void onDestroy() {
        mView = null;
    }


    @Override
    public void resetPassword(final Context context, String user) {
        mView.showProgress();
        if(user.length()==0) {
            mView.resetError(context.getString(R.string.error_invalid_login));
            mView.hideProgress();
        } else {
            Call<FormattedResp> respuesta = mService.resetPassword(Constants.CLIENT_ID, Constants.CLIENT_SECRET, user);

            respuesta.enqueue(new Callback<FormattedResp>() {
                @Override
                public void onResponse(Call<FormattedResp> call, Response<FormattedResp> response) {
                    mView.hideProgress();
                    if (response.isSuccessful()) {
                        FormattedResp resp = response.body();
                        String message = MessageManager.getMessage(context, resp.code());
                        mView.resetSuccess(message);

                    } else {
                        FormattedResp error = ParserError.parse(response);
                        String message = MessageManager.getMessage(context, error.code());
                        mView.resetError(message);
                    }

                }

                @Override
                public void onFailure(Call<FormattedResp> call, Throwable t) {
                    mView.hideProgress();

                    if(Excepts.isConnectionFailure(t)) {
                        String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                        mView.resetError(errorMessage);
                    }

                    // TODO VALIDAR CUANDO EN EL SERVER SALE UN ERROR 500, 403
                }
            });
        }
    }
}
