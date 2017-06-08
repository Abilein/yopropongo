package com.xiberty.propongo.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class AppSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String TAG = AppSyncAdapter.class.getSimpleName();

    // Interval at which to sync with the weather, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    // public static final int SYNC_INTERVAL = 60 * 60;
//    public static final int SYNC_INTERVAL = 10;
    public static final int SYNC_INTERVAL = 20;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    public AppSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        CouncilService councilService = WS.makeService(CouncilService.class);

        Log.e("COUNCIL>>>>", "RUN PERFORM");


        try {
            int pageNumber = 1;
            Call<List<Council>> call = councilService.getCouncils();
            List<Council> councils = call.execute().body();

            for (Council council: councils) {
                Log.e("COUNCIL>>>>", council.toString());
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


//        Call<List<Commission>> getCommissions();
//
//
//        Call<List<CouncilMan>> getCouncilMan();



//        Log.e(TAG, "->>> Starting SYNC <<<-");
//        ArrayList<PageResponse> pages;
//
//        Log.e(TAG, "endpoint: " + Constants.COUNCILMEN_ENDPOINT);
//        pages = SyncRequest.loadPaginatedRequests(Constants.COUNCILS_ENDPOINT, null, null);
//        CouncilOLD.savePagesOnProvider(getContext(), pages);
//
//        pages = SyncRequest.loadPaginatedRequests(Constants.COUNCILMEN_ENDPOINT, null, null);
//        CouncilMan.savePagesOnProvider(getContext(), pages);
//
//        pages = SyncRequest.loadPaginatedRequests(Constants.COMMISSIONS_ENDPOINT, null, null);
//        Commission.savePagesOnProvider(getContext(), pages);


//        //SIGN
//        Hashtable<String, String> headers = null;
////        SessionManager session = new SessionManager(getContext());
////        if (session.isLoggedIn()) {
//
//        if (UserStore.existCredential(getContext())) {
//            headers = new Hashtable<String, String>();
////            headers.put(Constants.AUTHORIZATION_KEY, session.makeSignature());
//            headers.put(Constants.AUTHORIZATION_KEY,"Bearer "+ UserStore.getAccessToken(getContext()));
//        }
//
//        pages = SyncRequest.loadPaginatedRequests(Constants.PROPOSALS_ENDPOINT, headers, null);
//        Proposal.savePagesOnProvider(getContext(), pages);
//
//
////        pages = SyncRequest.loadPaginatedRequests(Constants.ATTACHMENTS_ENPOINT, null, null);
////        Attachment.savePagesOnProvider(getContext(), pages);
////
////        pages = SyncRequest.loadPaginatedRequests(Constants.LINKS_ENPOINT, null, null);
////        Link.savePagesOnProvider(getContext(), pages);

    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = Constants.PACKAGE_NAME;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            android.content.SyncRequest request = new android.content.SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                Constants.PACKAGE_NAME, bundle);
    }

    public static Account getSyncAccount(Context context) {
        // GetRequest an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), Constants.SYNC_ACCOUNT_TYPE);

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        AppSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, Constants.PACKAGE_NAME, true);
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    public static int getSyncInterval(Context context) {
        return 10;
    }


}