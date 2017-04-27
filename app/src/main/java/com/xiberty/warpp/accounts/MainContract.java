package com.xiberty.warpp.accounts;

import android.content.Context;

import com.xiberty.warpp.credentials.responses.UserProfile;


public class MainContract {

    public interface View {
        void showProgress();
        void hideProgress();

        void setDrawer(UserProfile profile);

        void logoutSuccess();
        void logoutError(String message);

    }

    public interface Presenter {
        void logout(Context context);
    }
    
}
