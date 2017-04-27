package com.xiberty.warpp.accounts.fragments;


import android.content.Context;

import com.xiberty.warpp.accounts.AccountService;
import com.xiberty.warpp.contrib.Store;
import com.xiberty.warpp.contrib.api.Excepts;
import com.xiberty.warpp.contrib.api.FormattedResp;
import com.xiberty.warpp.contrib.api.MessageManager;
import com.xiberty.warpp.contrib.api.ParserError;
import com.xiberty.warpp.credentials.responses.UserProfile;

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

}
