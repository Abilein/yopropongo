package com.xiberty.propongo.council.fragments;


import android.content.Context;

import com.xiberty.propongo.db.Council;

import java.util.List;

/**
 * Created by growcallisaya on 3/5/17.
 */

public class CouncilDetailContract {

    public interface View{
        void showCouncils(List<Council> councils);
        void errorLoadCouncil(String errorMessage);
    }

    public interface Presenter{
        void getCouncils(Context context);
        void getCommissions(Context context);
        void getProposals(Context context);
        void getCouncilMen(Context context);
    }
}
