package com.xiberty.propongo.accounts.fragments;

import android.content.Context;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.AccountService;
import com.xiberty.propongo.accounts.EditProfileActivity;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.councils.NewProposalActivity;
import com.xiberty.propongo.councils.adapters.InboxProposalsAdapter;
import com.xiberty.propongo.councils.models.ProposalResponse;
import com.xiberty.propongo.credentials.responses.UserProfile;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import de.mateware.snacky.Snacky;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    @BindView(R.id.lblFullName)
    TextView lblFullName;
    @BindView(R.id.lblUsername)
    TextView lblUsername;
    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;

    View rootView;
    @BindView(R.id.MyProposalslist)
    ListView MyProposalslist;
    @BindView(R.id.placeholder_text)
    TextView placeholderText;
    @BindView(R.id.placeholder)
    LinearLayout placeholder;
    @BindView(R.id.listContent)
    RelativeLayout listContent;
    @BindView(R.id.progressBarContent)
    RelativeLayout progressBarContent;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    ProfilePresenter presenter;
    public static String TAG = ProfileFragment.class.getSimpleName();
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        context = rootView.getContext();
        ButterKnife.bind(this, rootView);

        //Toolbar
        setToolbar();

        AccountService service = WS.makeService(AccountService.class, Store.getCredential(getContext()));
        presenter = new ProfilePresenter(this, service);
        presenter.getMyProposals(context);
        setUserProfile();

        return rootView;
    }

    public void setUserProfile() {
        UserProfile profile = Store.getProfile(getContext());

        Log.e("PROFILE IN SET", profile.toString());
        Log.e("ACCESS_TOKEN", Store.getAccessToken(getContext()));

        lblFullName.setText(profile.fullName());
        lblUsername.setText(profile.email());

        if (profile.photo() != null && profile.photo().length() > 0) {
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
        toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_invisible));
        toolbar.setVisibility(View.VISIBLE);
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

    @Override
    public void showProposals(List<ProposalResponse> proposalResponse) {
        InboxProposalsAdapter adapter = new InboxProposalsAdapter(context, proposalResponse, TAG);
        if (adapter.getCount() == 0) {
            placeholder.setVisibility(View.VISIBLE);
            placeholderText.setText("No tienes propuestas ");
            MyProposalslist.setVisibility(View.GONE);
        } else {
            MyProposalslist.setAdapter(adapter);
        }
    }

    @Override
    public void showProgress() {
        progressBarContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarContent.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        placeholder.setVisibility(View.VISIBLE);
        placeholderText.setText("No tienes propuestas ");
        MyProposalslist.setVisibility(View.GONE);

    }

    @OnClick(R.id.btnCrearPropuesta)
    public void crearPropuesta(View view) {
        Intent intent = new Intent(context, NewProposalActivity.class);
        startActivity(intent);
    }

}
