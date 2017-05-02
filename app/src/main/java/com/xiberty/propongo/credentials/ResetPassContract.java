package com.xiberty.propongo.credentials;

import android.content.Context;


public class ResetPassContract {

    public interface View {

        void showProgress();
        void hideProgress();

        void sendHandler();
        void resetSuccess(String message);
        void resetError(String message);

    }

    public interface Presenter {
        void onCreate();
        void onDestroy();

        void resetPassword(Context context, String email);
    }
}
