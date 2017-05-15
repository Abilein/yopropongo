package com.xiberty.propongo.commission.fragments;

import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.db.Commission;
import com.xiberty.propongo.db.AppDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
