package com.xiberty.propongo.contrib.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xiberty.propongo.R;

public class ToolbarBaseFragment extends Fragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(null, null);
    }

    public void setToolbar(String title, String subtitle) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (toolbar!=null) {
            toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_invisible));
            toolbar.setVisibility(android.view.View.VISIBLE);
            toolbar.setTitle(title);
            toolbar.setSubtitle(subtitle);
        }
    }

    public void setHeader(View view, String title, String subtitle) {
        if (view != null) {
            TextView headerTitle = (TextView) view.findViewById(R.id.headerTitle);
            TextView headerSubttle = (TextView) view.findViewById(R.id.headerSubtitle);
            if (headerTitle != null) headerTitle.setText(title);
            if (headerSubttle != null) headerSubttle.setText(subtitle);
        }
    }
}
