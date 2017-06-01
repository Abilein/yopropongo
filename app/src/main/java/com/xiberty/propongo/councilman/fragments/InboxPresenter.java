package com.xiberty.propongo.councilman.fragments;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.db.Commission;

import java.util.List;


public class InboxPresenter implements InboxContract.Presenter {

    String TAG = InboxPresenter.class.getSimpleName();
    private CouncilService service;
    private InboxContract.CommissionView view;

    public InboxPresenter(CouncilService service, InboxContract.CommissionView view) {
        this.service = service;
        this.view = view;
    }


}
