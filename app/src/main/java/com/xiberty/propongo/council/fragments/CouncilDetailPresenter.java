package com.xiberty.propongo.council.fragments;


import android.content.Context;
import android.text.LoginFilter;
import android.util.Log;

import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.council.responses.Council;
import com.xiberty.propongo.db.Council_T;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by growcallisaya on 3/5/17.
 */

public class CouncilDetailPresenter implements CouncilDetailContract.Presenter {
    private CouncilService mService;
    private CouncilDetailFragment mView;
    private CouncilDetailRepository mRepository;

    public CouncilDetailPresenter(CouncilDetailFragment mView, CouncilService mService) {
        this.mView = mView;
        this.mService = mService;
    }


    @Override
    public void getCouncils(final Context context) {
        Call<List<Council>> respuesta = mService.getCouncils();
        respuesta.enqueue(new Callback<List<Council>>() {
            @Override
            public void onResponse(Call<List<Council>> call, Response<List<Council>> response) {
                if (response.isSuccessful()) {

                    //TODO Make it in CouncilDetailRepository
                    //Settings the Database
//                    Council_T table_council =  new Council_T();
//                    table_council.name="Concejo Municipal de La Paz | Base";
//                    table_council.department="LA_PAZ";
//                    table_council.creation_date="2017-05-03";
//                    table_council.president=1;
//                    table_council.vice_president=2;
//                    table_council.secretary=3;
//                    table_council.vocal_a=4;
//                    table_council.vocal_b=5;
//                    table_council.logo="";
//                    table_council.save();

                    Log.e("CouncilDetailPresenter", response.message());
                    List<Council> items = response.body();
                    mView.showCouncils(items);
                } else {
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    Log.e("CouncilDetailPresenter", errorMessage);
                    mView.errorLoadCouncil(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Council>> call, Throwable t) {
                 Log.e("CouncilDetailPresenter", t.getCause().getMessage());
                mView.errorLoadCouncil(t.getCause().getMessage());
            }
        });
    }
}
