package com.xiberty.propongo.accounts.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.views.XTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AboutFragment extends Fragment {

    public View rootView;

    @BindView(R.id.lblPage) XTextView lblPage;
    @BindView(R.id.lblContact) XTextView lblContact;
    @BindView(R.id.lblEmail) XTextView lblEmail;
    @BindView(R.id.lblPhone) XTextView lblPhone;
    @BindView(R.id.imgLogo) ImageView imgLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, rootView);
        setToolbar();
        return rootView;
    }

    public void setToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_invisible));
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
    }

    @OnClick(R.id.lblPage)
    public void goPage(){
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://xiberty.com"));
        startActivity(intent);
    }

    @OnClick(R.id.lblContact)
    public void goContact(){
        goEmail();
    }

    @OnClick(R.id.lblEmail)
    public void goEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:info@xiberty.com"));
        try {
            startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {

        }
    }

    @OnClick(R.id.lblPhone)
    public void goPhone(){
        goEmail();
    }

    @OnClick(R.id.imgLogo)
    public void goLogo(){
        goPage();
    }
}
