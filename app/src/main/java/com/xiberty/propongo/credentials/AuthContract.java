package com.xiberty.propongo.credentials;

import android.content.Context;

public class AuthContract {

    public interface AuthView {

    }

    public interface AuthPresenter {
        void checkAuth(Context context);
    }
}
