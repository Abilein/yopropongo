package com.xiberty.propongo.commission.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.db.Commission;

import java.util.List;

public class CommissionFragment extends Fragment implements CommissionContract.CommissionView{
    private static final String TAG = CommissionFragment.class.getSimpleName();
    CouncilService mService;
    CommissionPresenter presenter;
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
        rootView = inflater.inflate(R.layout.fragment_commissions, container, false);
        if(rootView!=null) {
            mService = WS.makeService(CouncilService.class, Store.getCredential(this.getContext()));
            presenter = new CommissionPresenter(mService,this);
            presenter.getCommissionsFromDB();
        }
        return rootView;
    }

    @Override
    public void loadCommissions(List<Commission> commissions) {
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        Log.e(TAG,"COMISIONES"+ commissions);
        listView.setAdapter(new CommissionAdapter(getActivity(), commissions));
    }
}
