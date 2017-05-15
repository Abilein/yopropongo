package com.xiberty.propongo.commission.fragments;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.db.Commission;

import java.util.List;

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
    public void getCommissionsFromDB() {
        /** Getting Commissions with DBflow **/
        List<Commission> commissions = SQLite.select().
                from(Commission.class).queryList();
        view.loadCommissions(commissions);
    }
}
