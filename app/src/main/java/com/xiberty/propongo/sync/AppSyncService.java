package com.xiberty.propongo.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AppSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static AppSyncAdapter appSyncAdapter = null;

    @Override
    public void onCreate() {
//        Log.d("SYNC SERVICE", "onCreate - DemandSyncService");
        synchronized (sSyncAdapterLock) {
            if (appSyncAdapter == null) {
                appSyncAdapter = new AppSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return appSyncAdapter.getSyncAdapterBinder();
    }
}