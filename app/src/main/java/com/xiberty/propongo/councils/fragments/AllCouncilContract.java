package com.xiberty.propongo.councils.fragments;


import android.content.Context;

import com.xiberty.propongo.database.Council;

import java.util.List;

public class AllCouncilContract {

    public interface View{
        void updateCouncils(List<Council> councils);
        void showError(String errorMessage);
    }

    public interface Presenter{
        void getCouncils(Context context);
    }
}
