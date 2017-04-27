package com.xiberty.warpp.accounts.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiberty.warpp.R;
import com.xiberty.warpp.accounts.AccountService;
import com.xiberty.warpp.accounts.EditProfileActivity;
import com.xiberty.warpp.contrib.Store;
import com.xiberty.warpp.contrib.api.WS;
import com.xiberty.warpp.credentials.responses.UserProfile;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import de.mateware.snacky.Snacky;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    @BindView(R.id.lblFullName) TextView lblFullName;
    @BindView(R.id.lblUsername) TextView lblUsername;
    @BindView(R.id.imgAvatar) CircleImageView imgAvatar;

    android.view.View rootView;

    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    ProfilePresenter presenter;

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, rootView);

        //Toolbar
        setToolbar();

        AccountService service = WS.makeService(AccountService.class, Store.getCredential(getContext()));
        presenter = new ProfilePresenter(this, service);
        setUserProfile();

        return rootView;
    }

    public void setUserProfile(){
        UserProfile profile = Store.getProfile(getContext());

        Log.e("PROFILE IN SET", profile.toString());
        Log.e("ACCESS_TOKEN", Store.getAccessToken(getContext()));

        lblFullName.setText(profile.fullName());
        lblUsername.setText(profile.email());

        if(profile.photo() != null && profile.photo().length() > 0){
            Glide.with(getActivity())
                .load(profile.photo())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgAvatar);
        } else {
            Glide.with(getActivity())
                .load(R.drawable.avatar)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgAvatar);
        }

    }

    public void setToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_translucent));
        toolbar.setVisibility(android.view.View.VISIBLE);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
    }


    @Override
    public void onResume() {
        super.onResume();
        setUserProfile();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.profile_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getProfileError(String message) {
        Snacky.builder()
                .setActivty(getActivity())
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
    }

}
