package com.xiberty.propongo.councils.fragments;

import com.xiberty.propongo.councils.CouncilService;


public class CommissionsPresenter implements CommissionsContract.Presenter {

    String TAG = CommissionsPresenter.class.getSimpleName();
    private CouncilService service;
    private CommissionsContract.View view;

    public CommissionsPresenter(CouncilService service, CommissionsContract.View view) {
        this.service = service;
        this.view = view;
    }


}
