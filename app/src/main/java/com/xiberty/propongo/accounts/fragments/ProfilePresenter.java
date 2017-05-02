package com.xiberty.propongo.accounts.fragments;


import com.xiberty.propongo.accounts.AccountService;


public class ProfilePresenter implements ProfileContract.Presenter {

    private AccountService mService;
    private ProfileFragment mView;

    public ProfilePresenter(ProfileFragment mView, AccountService mService) {
        this.mView = mView;
        this.mService = mService;
    }

}
