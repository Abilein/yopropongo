package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.util.Log;

import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.models.NewProposalRespse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InboxPresenter implements InboxContract.Presenter {

    String TAG = InboxPresenter.class.getSimpleName();
    private CouncilService service;
    private InboxContract.CommissionView view;

    public InboxPresenter(CouncilService service, InboxContract.CommissionView view) {
        this.service = service;
        this.view = view;
    }

    @Override
    public void getInbox(Context context) {
        Call<List<NewProposalRespse>> inboxCall = service.getCouncilMenInbox(Store.getAccessToken(context));
        inboxCall.enqueue(new Callback<List<NewProposalRespse>>() {
            @Override
            public void onResponse(Call<List<NewProposalRespse>> call, Response<List<NewProposalRespse>> response) {
                if (response.isSuccessful()){
                    List<NewProposalRespse> proposalRespses = response.body();
                    view.showProposals(proposalRespses);
                }else {
                    Log.e(TAG,"1 Inbox error 'cause "+response.body());
                }
            }

            @Override
            public void onFailure(Call<List<NewProposalRespse>> call, Throwable t) {
                Log.e(TAG,"2 Inbox error 'cause "+t.getMessage());
            }
        });

    }
}
