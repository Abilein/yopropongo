package com.xiberty.warpp.accounts.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiberty.warpp.R;

public class SettingsFragment extends Fragment {

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setToolbar();
        putFragment();
        return rootView;
    }

    public void setToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_invisible));
        toolbar.setVisibility(android.view.View.VISIBLE);
        toolbar.setTitle("");
        toolbar.setSubtitle(getResources().getString(R.string.pref_settings));
    }

    public void putFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ConfigFragment configFragment = new ConfigFragment();
        fragmentTransaction.add(R.id.settingsContent, configFragment);
        fragmentTransaction.commit();
    }
}
