package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.adapters.CommissionAdapter;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.Council;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CommissionsFragment extends ToolbarBaseFragment implements CommissionsContract.View {
    private static final String TAG = CommissionsFragment.class.getSimpleName();
    CouncilService mService;
    CommissionsPresenter presenter;
    View rootView = null;
    public Context context;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

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
        context = rootView.getContext();
        ButterKnife.bind(this,rootView);

        //Default Council
        Council selectedCouncil = Store.getDefaultCouncil(context);
        setHeader(rootView, getString(R.string.menu_commisions).toUpperCase(), selectedCouncil.name());

        //Setting the Directive
        setCommissionsCards();

        //Service for Presenter
        CouncilService cservice = WS.makeService(CouncilService.class);
        presenter = new CommissionsPresenter(cservice, this);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshCommissions();
            }
        });
        return rootView;
    }

    private void refreshCommissions() {
        presenter.getCouncils(context);
    }

    private void setCommissionsCards() {
        ArrayList<Commission> commissions = Store.getCommissions(context);
        for (Commission commission : commissions)
            Log.e("Comission", commission.toString());

        if (commissions != null) {
            CommissionAdapter adapter = new CommissionAdapter(getActivity().getApplicationContext(), commissions);
            ListView listView = (ListView) rootView.findViewById(R.id.listView);
            LinearLayout placeholder = (LinearLayout) rootView.findViewById(R.id.placeholder);
            TextView placeholder_text = (TextView) rootView.findViewById(R.id.placeholder_text);
            if (adapter.getCount() == 0) {
                placeholder.setVisibility(View.VISIBLE);
                placeholder_text.setText("NO EXISTE COMISIONES");
                listView.setVisibility(View.GONE);
            } else {
                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void updateCouncils(List<Council> councils) {
        Store.saveCouncils(context, councils);
        for (Council council : councils) {
            if (council.name().equals(Store.getDefaultCouncil(context).name)) {
                Store.setDefaultCouncil(context, council);
                Log.e("MESSAGE", "Nuevo consejo es " + council.name());
            }
        }
        swipeContainer.setRefreshing(false);
        setCommissionsCards();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(context, "Error al actualizar, inténtelo más tarde!", Toast.LENGTH_SHORT).show();
        swipeContainer.setRefreshing(false);

    }

}
