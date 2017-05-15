package com.xiberty.propongo.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private AppAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new AppAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}

