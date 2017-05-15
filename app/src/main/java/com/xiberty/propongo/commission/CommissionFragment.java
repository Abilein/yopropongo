package com.xiberty.propongo.commission;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.xiberty.propongo.council.responses.Commission;

import java.util.List;

/**
 * Created by growcallisaya on 15/5/17.
 */

public class CommissionFragment extends Fragment implements CommissionContract.CommissionView{
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
            presenter.getCommissions(this.getContext());


        }
        return rootView;
    }

    @Override
    public void loadCommissions(List<Commission> commissions) {
        //TODO Load Commissions from DBflow
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(new CommissionAdapter(getActivity(), commissions));
    }
}
