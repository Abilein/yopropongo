package com.xiberty.warpp.onset;

import android.content.Context;
import android.content.Intent;

import com.xiberty.warpp.accounts.MainActivity;
import com.xiberty.warpp.contrib.Store;


public class StartPresenter implements StartContract.StartActionListener {
    private StartContract.View mView;

    public StartPresenter(StartContract.View mView) {
        this.mView = mView;
    }

    public void isLoggedIn(Context context) {
        if (mView != null) {
            if(Store.isLoggedIn(context)) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        }
    }

}
