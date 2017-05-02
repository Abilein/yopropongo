package com.xiberty.propongo.credentials;

import android.content.Context;


public class RegisterContract {

    public interface View {

        void signupHandler();
        void signupError(String message);
        void signupSuccess(String message, String username, String password);

        void getCredentialError(String message);
        void getCredentialSuccess();

        void showProgress();
        void hideProgress();
    }

    public interface Presenter {
        void register(Context context, String username, String email, String password);
        void getCredentials(Context context, String username, String password);
    }
}
