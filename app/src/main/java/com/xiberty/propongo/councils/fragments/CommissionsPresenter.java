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


public class CommissionsPresenter implements CommissionsContract.Presenter {

    String TAG = CommissionsPresenter.class.getSimpleName();
    private CouncilService service;
    private CommissionsContract.View view;

    public CommissionsPresenter(CouncilService service, CommissionsContract.View view) {
        this.service = service;
        this.view = view;
    }

    @Override
    public void getCouncils(final Context context) {
        Call<List<Council>> councilsCall = service.getCouncils();
        councilsCall.enqueue(new Callback<List<Council>>() {
            @Override
            public void onResponse(Call<List<Council>> call, Response<List<Council>> response) {
                if (response.isSuccessful()) {
                    List<Council> councils = response.body();
                    view.updateCouncils(councils);
                } else {
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    view.showError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Council>> call, Throwable t) {
                Log.e("MainPresenter",t.getCause()+"" );
            }
        });
    }



}
