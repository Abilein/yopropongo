package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.Council;

import java.util.List;


public class CommissionsFragment extends ToolbarBaseFragment implements CommissionsContract.View{
    private static final String TAG = CommissionsFragment.class.getSimpleName();
    CouncilService mService;
    CommissionsPresenter presenter;
    View rootView=null;
    public Context context;

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


        //Default Council
        Council selectedCouncil = Store.getDefaultCouncil(context);
        setHeader(rootView, getString(R.string.menu_directive).toUpperCase(), selectedCouncil.name());


        context = rootView.getContext();
        List<Commission> commissions = Store.getCommissions(context);
        for (Commission commission : commissions){
            Log.e("Commissiones", commission+"");
        }




        return rootView;
    }

}
