package com.xiberty.propongo.commission.fragments;

import com.xiberty.propongo.db.Commission;

import java.util.List;


public class CommissionContract {

    public interface Presenter{
        void getCommissionsFromDB();
    }

    public interface CommissionView{
        void loadCommissions(List<Commission> commissions);
    }
}
