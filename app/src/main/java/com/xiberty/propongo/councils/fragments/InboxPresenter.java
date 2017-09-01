package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.util.Log;

import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.models.NewProposalResponse;

import java.util.ArrayList;
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
        view.showProgress();
        Call<List<NewProposalResponse>> inboxCall = service.getCouncilMenInbox(Store.getAccessToken(context));
        inboxCall.enqueue(new Callback<List<NewProposalResponse>>() {
            @Override
            public void onResponse(Call<List<NewProposalResponse>> call, Response<List<NewProposalResponse>> response) {
                if (response.isSuccessful()){
                    view.hideProgress();
                    List<NewProposalResponse> proposalResponses = response.body();
                    List<NewProposalResponse> newProposals = new ArrayList<>();

                    for (NewProposalResponse proposalResponse: proposalResponses){
                        if (proposalResponse.status.equals("PROPOSED"))
                            newProposals.add(proposalResponse);
                    }

                    view.showProposals(newProposals);
                }else {
                    view.hideProgress();
                    view.showInboxError();
                    Log.e(TAG,"1 Inbox error 'cause "+response.body());
                }
            }

            @Override
            public void onFailure(Call<List<NewProposalResponse>> call, Throwable t) {
                view.hideProgress();
                view.showInboxError();
                Log.e(TAG,"2 Inbox error 'cause "+t.getMessage());
            }
        });

    }
}
