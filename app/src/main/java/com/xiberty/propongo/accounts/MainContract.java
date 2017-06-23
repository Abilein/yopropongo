package com.xiberty.propongo.accounts;

import android.content.Context;

import com.xiberty.propongo.credentials.responses.UserProfile;
import com.xiberty.propongo.database.Council;

import java.util.List;


public class MainContract {

    public interface View {
        void showProgress();
        void hideProgress();

        void setDrawer(UserProfile profile,List<Council> councils,Council defaultCouncil);

        void logoutSuccess();


        void showCouncils(List<Council> councils);
        void councilmenSuccess();
        void proposalsSuccess();

        void showError(String message);

        void setCouncilinDrawer(List<Council> councils,Council council);
    }

    public interface Presenter {
        void logout(Context context);

        void getCouncils(Context context);
        void getCouncilmen(Context context);
        void getProposals(Context context);
    }
    
}
