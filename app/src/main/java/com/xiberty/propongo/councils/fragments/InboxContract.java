package com.xiberty.propongo.councils.fragments;


import android.content.Context;

import com.xiberty.propongo.councils.models.NewProposalRespse;

import java.util.List;

public class InboxContract {

    public interface Presenter{
        void getInbox(Context context);
//        void getCommissionsFromDB();
    }

    public interface CommissionView{
        void showProposals(List<NewProposalRespse> proposalRespses);
//        void loadCommissions(List<Commission> commissions);
    }
}
