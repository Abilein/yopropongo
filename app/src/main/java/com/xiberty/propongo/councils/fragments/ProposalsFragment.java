package com.xiberty.propongo.councils.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.councils.CouncilService;

import butterknife.ButterKnife;


public class ProposalsFragment extends ToolbarBaseFragment implements InboxContract.CommissionView {
    private static final String TAG = ProposalsFragment.class.getSimpleName();
    CouncilService mService;
    InboxPresenter presenter;
    View rootView=null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, rootView);

        setHeader(rootView, getString(R.string.menu_proposals).toUpperCase(), "CONCEJO MUNICIPAL DE LA PAZ");

        return rootView;
    }


}
