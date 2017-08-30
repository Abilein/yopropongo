package com.xiberty.propongo.councils.fragments;


import android.content.Context;
import android.util.Log;

import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.database.Council;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DirectivePresenter implements DirectiveContract.Presenter {

    private CouncilService mService;
    private DirectiveFragment mView;
    private String TAG = DirectivePresenter.class.getSimpleName();

    public DirectivePresenter(DirectiveFragment mView, CouncilService mService) {
        this.mView = mView;
        this.mService = mService;
    }

    @Override
    public void getCouncils(final Context context) {
        Call<List<Council>> councilsCall = mService.getCouncils();
        councilsCall.enqueue(new Callback<List<Council>>() {
            @Override
            public void onResponse(Call<List<Council>> call, Response<List<Council>> response) {
                if (response.isSuccessful()) {
                    List<Council> councils = response.body();
                    mView.updateCouncils(councils);
                } else {
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.showError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Council>> call, Throwable t) {
                Log.e("MainPresenter",t.getCause()+"" );
            }
        });
    }


}
