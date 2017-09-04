package com.xiberty.propongo.accounts;

import android.content.Context;

import com.xiberty.propongo.credentials.responses.UserProfile;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.Proposal;

import java.util.List;


public class MainContract {

    public interface View {
        void showProgress();
        void hideProgress();
        void setDrawer(UserProfile profile);
        void logoutSuccess();
        void showCouncils(List<Council> councils);
        void showError(String message);
        void setCouncilinDrawer();
    }

    public interface Presenter {
        void logout(Context context);

        void saveCouncils(Context context);
        void saveCouncilmen(Context context);
        void saveProposals(Context context);
        void saveCommissions(Context context);
    }
    
}
