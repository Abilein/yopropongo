package com.xiberty.propongo.accounts.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.BuildConfig;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.xiberty.propongo.R;


public class ConfigFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {

        setPreferencesFromResource(R.xml.preferences, rootKey);
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("notificationPref")) {
            if (sharedPreferences.getBoolean(key, true)) {
                if (BuildConfig.DEBUG) {
                    Log.d("LAG", "settings notificationPref changed: addPeriodicSync()");
                }
            } else {
                if (BuildConfig.DEBUG) {
                    Log.d("LAG", "settings notificationPref changed: removePeriodicSync()");
                }
            // TODO get AbstractSyncHelper;

            }
        }
    }
}
