package com.xiberty.propongo.commission.fragments;

import com.xiberty.propongo.db.Commission;

import java.util.List;

/**
 * Created by growcallisaya on 15/5/17.
 */

public class CommissionContract {

    public interface Presenter{
        void getCommissionsFromDB();
    }

    public interface CommissionView{
        void loadCommissions(List<Commission> commissions);
    }
}
