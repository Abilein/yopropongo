package com.xiberty.propongo.contrib.fragments;


import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.xiberty.propongo.R;

public class ToolbarBaseFragment extends Fragment {

    public void setToolbar(String title, String subtitle) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar!=null) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_translucent));
            toolbar.setVisibility(android.view.View.VISIBLE);
            toolbar.setTitle(title);
            toolbar.setSubtitle(subtitle);
        }

    }
}
