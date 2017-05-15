package com.xiberty.propongo.commission;

import android.content.Context;
import android.util.Log;

import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.council.responses.Commission;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by growcallisaya on 15/5/17.
 */

public class CommissionPresenter implements CommissionContract.Presenter {

    String TAG = CommissionPresenter.class.getSimpleName();
    private CouncilService service;
    private CommissionContract.CommissionView view;

    public CommissionPresenter(CouncilService service, CommissionContract.CommissionView view) {
        this.service = service;
        this.view = view;
    }


    @Override
    public void getCommissions(Context context) {
        Call<List<Commission>> call = service.getCommissions();
        Callback<List<Commission>> callback = new Callback<List<Commission>>() {
            @Override
            public void onResponse(Call<List<Commission>> call, Response<List<Commission>> response) {
                if (response.isSuccessful()){
                    Log.e(TAG,response.body()+"");
                    List<Commission> commissions = response.body();

                    //TODO Set Commissions with DBFLow
                    view.loadCommissions(commissions);
                }else{
                    Log.e(TAG,response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Commission>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        };
        call.enqueue(callback);

    }
}
