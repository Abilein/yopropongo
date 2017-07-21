package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.NewProposalActivity;
import com.xiberty.propongo.councils.adapters.ProposalsAdapter;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProposalsFragment extends ToolbarBaseFragment implements ProposalsContract.View {
    private static final String TAG = ProposalsFragment.class.getSimpleName();
    View rootView = null;
    Context context;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.placeholder_text)
    TextView placeholderText;
    @BindView(R.id.placeholder)
    LinearLayout placeholder;
    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;

    private Council selectedCouncil;

    ProposalsPresenter presenter;
    CouncilService service;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        context = rootView.getContext();
        ButterKnife.bind(this, rootView);

        //Default Council
        selectedCouncil = Store.getDefaultCouncil(context);
        setHeader(rootView, getString(R.string.menu_proposals).toUpperCase(), selectedCouncil.name());

        List<ProposalDB> proposals = SQLite.select().
                from(ProposalDB.class).
                where(ProposalDB_Table.council.is(selectedCouncil.id)).
                queryList();
        setProposals(proposals);

        service = WS.makeService(CouncilService.class);
        presenter = new ProposalsPresenter(service,this);

        return rootView;
    }

    private void setProposals(List<ProposalDB> proposals) {
        ProposalsAdapter adapter = new ProposalsAdapter(context, proposals,TAG);
        if (adapter.getCount() == 0) {
            placeholder.setVisibility(View.VISIBLE);
            placeholderText.setText("NO EXISTEN PROPUESTAS");
            listView.setVisibility(View.GONE);
        } else {
            listView.setAdapter(adapter);
            btnAdd.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.proposal_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                updateProposalDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateProposalDB() {
        
        //TODO update proposals
        presenter.getProposals(context);
        List<ProposalDB> proposals = SQLite.select().
                from(ProposalDB.class).
                where(ProposalDB_Table.council.is(selectedCouncil.id)).
                queryList();
        setProposals(proposals);
        Toast.makeText(context, "Actualizando...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnAdd)
    public void AddProposals(View view){
        Intent intent = new Intent(context, NewProposalActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(context, "Error al Actualizar", Toast.LENGTH_SHORT).show();
    }
}
