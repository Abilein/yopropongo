package com.xiberty.propongo.councilman.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ListView;

import com.xiberty.propongo.R;
import com.xiberty.propongo.commission.adapter.CommissionAdapter;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.db.Commission;

import java.util.List;

import butterknife.ButterKnife;

public class InboxFragment extends ToolbarBaseFragment implements InboxContract.CommissionView{
    private static final String TAG = InboxFragment.class.getSimpleName();
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
        rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        ButterKnife.bind(this, rootView);

        //Toolbar
        setToolbar(null, "INBOX");

        return rootView;
    }


}
