package com.xiberty.warpp.credentials;

import android.content.Context;

import com.xiberty.warpp.accounts.AccountService;
import com.xiberty.warpp.credentials.responses.OAuthCredential;


public class LoginContract {

    public interface View {
        void showProgress();
        void hideProgress();

        void signInHandler();
        void forgotPasswordHandler();
        void facebookSignHandler();

        void signInSuccess(OAuthCredential credential);
        void signInError(String message);

        void getProfileSuccess();
        void getProfileError(String message);

    }

    public interface Presenter {

        void onCreate();
        void onDestroy();

        void getProfile(Context contex, AccountService aService);
        void login(Context context, String user, String password);
        void facebookLogin(Context context, String facebookToken);
    }


}
