package com.xiberty.propongo.accounts.fragments;


import android.content.Context;

import com.xiberty.propongo.accounts.AccountService;
import com.xiberty.propongo.councils.models.ProposalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfilePresenter implements ProfileContract.Presenter {

    private AccountService mService;
    private ProfileFragment mView;

    public ProfilePresenter(ProfileFragment mView, AccountService mService) {
        this.mView = mView;
        this.mService = mService;
    }


    @Override
    public void getMyProposals(Context context) {
        mView.showProgress();
        Call<List<ProposalResponse>> responseCall = mService.getMyProposals();
        responseCall.enqueue(new Callback<List<ProposalResponse>>() {
            @Override
            public void onResponse(Call<List<ProposalResponse>> call, Response<List<ProposalResponse>> response) {
                if (response.isSuccessful()){
                    mView.showProposals(response.body());
                    mView.hideProgress();
                }else{
                    mView.showError();
                    mView.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<List<ProposalResponse>> call, Throwable t) {
                mView.showError();
                mView.hideProgress();

            }
        });
    }
}
