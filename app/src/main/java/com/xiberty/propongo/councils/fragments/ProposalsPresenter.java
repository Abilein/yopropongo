package com.xiberty.propongo.councils.fragments;

import com.xiberty.propongo.councils.CouncilService;


public class ProposalsPresenter implements InboxContract.Presenter {

    String TAG = ProposalsPresenter.class.getSimpleName();
    private CouncilService service;
    private InboxContract.CommissionView view;

    public ProposalsPresenter(CouncilService service, InboxContract.CommissionView view) {
        this.service = service;
        this.view = view;
    }


}
