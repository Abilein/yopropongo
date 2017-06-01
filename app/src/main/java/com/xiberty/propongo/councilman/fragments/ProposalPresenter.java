package com.xiberty.propongo.councilman.fragments;

import com.xiberty.propongo.council.CouncilService;


public class ProposalPresenter implements InboxContract.Presenter {

    String TAG = ProposalPresenter.class.getSimpleName();
    private CouncilService service;
    private InboxContract.CommissionView view;

    public ProposalPresenter(CouncilService service, InboxContract.CommissionView view) {
        this.service = service;
        this.view = view;
    }


}
