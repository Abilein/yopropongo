package com.xiberty.propongo.councils.fragments;


import android.content.Context;

public class ProposalsContract {

    public interface Presenter{
        void getProposals(final Context context);
    }

    public interface View{
        void showError(String errorMessage);
//        void loadCommissions(List<Commission> commissions);
    }
}
