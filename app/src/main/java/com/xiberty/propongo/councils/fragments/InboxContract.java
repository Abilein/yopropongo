package com.xiberty.propongo.councils.fragments;


import android.content.Context;

import com.xiberty.propongo.councils.models.NewProposalResponse;

import java.util.List;

public class InboxContract {

    public interface Presenter{
        void getInbox(Context context);
//        void getCommissionsFromDB();
    }

    public interface CommissionView{
        void showProposals(List<NewProposalResponse> proposalRespses);

        void showProgress();
        void hideProgress();

        void showInboxError();
//        void loadCommissions(List<Commission> commissions);
    }
}
