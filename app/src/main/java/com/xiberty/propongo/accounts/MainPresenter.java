package com.xiberty.propongo.accounts;

import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.Excepts;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.OAuthCollection;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.credentials.CredentialService;
import com.xiberty.propongo.credentials.responses.UserProfile;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.Council_Table;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private AccountService aService;
    private CredentialService cService;
    private CouncilService ccService;
    private int counter = 0;

    public MainPresenter(MainContract.View mView, AccountService aService, CredentialService cService, CouncilService ccService) {
        this.mView = mView;
        this.aService = aService;
        this.cService = cService;
        this.ccService = ccService;
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
                        mView.showError(errorMessage);
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
                        mView.showError(errorMessage);
                        mView.logoutSuccess();

                    }

                    // TODO VALIDAR CUANDO EN EL SERVER SALE UN ERROR 500, 403
                }
            });

        }
    }

    @Override
    public void getCouncils(final Context context) {

        mView.showProgress();
        Call<List<Council>> councilsCall = ccService.getCouncils();
        councilsCall.enqueue(new Callback<List<Council>>() {
            @Override
            public void onResponse(Call<List<Council>> call, Response<List<Council>> response) {

                if (response.isSuccessful()) {
                    mView.hideProgress();
                    List<Council> councils = response.body();
                    mView.showCouncils(councils);

                } else {
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.showError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Council>> call, Throwable t) {
                mView.hideProgress();
                Log.e("MainPresenter",t.getCause()+"" );
            }
        });
    }

    @Override
    public void getCouncilmen(final Context context) {
        Council defaultCouncil = Store.getDefaultCouncil(context);
        Call<List<CouncilMan>> councilmenCall = ccService.getCouncilMan(defaultCouncil.id()+"");
        councilmenCall.enqueue(new Callback<List<CouncilMan>>() {
            @Override
            public void onResponse(Call<List<CouncilMan>> call, Response<List<CouncilMan>> response) {
                if (response.isSuccessful()){
                    Log.e("Main Presenter", response.body()+"");
                    Store.saveCouncilman(context,response.body());
                }else{
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.showError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<CouncilMan>> call, Throwable t) {
                Log.e("MainPresenter",t.getCause()+"" );
            }
        });

    }

    @Override
    public void getProposals(Context context) {

    }

    @Override
    public void getCommissions(final Context context) {
        Council defaultCouncil = Store.getDefaultCouncil(context);
        Call<List<Commission>> councilmenCall = ccService.getCommissions(defaultCouncil.id()+"");
        councilmenCall.enqueue(new Callback<List<Commission>>() {
            @Override
            public void onResponse(Call<List<Commission>> call, Response<List<Commission>> response) {
                if (response.isSuccessful()){
                    Log.e("Main Presenter", response.body()+"");
                    Store.saveCommissions(context,response.body());
                }else{
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.showError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Commission>> call, Throwable t) {
                Log.e("MainPresenter",t.getCause()+"" );
            }
        });
    }


}
