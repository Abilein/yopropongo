package com.xiberty.propongo.credentials;


import android.content.Context;
import android.content.Intent;

import com.xiberty.propongo.contrib.Store;

public class AuthPresenter implements AuthContract.AuthPresenter{
    AuthContract.AuthView view;

    public AuthPresenter(AuthContract.AuthView view) {
        this.view = view;
    }

    @Override
    public void checkAuth(Context context) {
        if (view != null) {
            if(!Store.isLoggedIn(context)) {
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        }
    }
}
