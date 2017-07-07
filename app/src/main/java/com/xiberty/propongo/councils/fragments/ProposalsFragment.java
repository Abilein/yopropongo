package com.xiberty.propongo.councils.fragments;

import android.content.Context;
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
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.adapters.ProposalsAdapter;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProposalsFragment extends ToolbarBaseFragment implements InboxContract.CommissionView {
    private static final String TAG = ProposalsFragment.class.getSimpleName();
    CouncilService mService;
    InboxPresenter presenter;
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
        Council selectedCouncil = Store.getDefaultCouncil(context);
        setHeader(rootView, getString(R.string.menu_proposals).toUpperCase(), selectedCouncil.name());

        List<ProposalDB> proposals = SQLite.select().
                from(ProposalDB.class).
                where(ProposalDB_Table.council.is(selectedCouncil.id)).
                queryList();
        setProposals(proposals);

        return rootView;
    }

    private void setProposals(List<ProposalDB> proposals) {
        ProposalsAdapter adapter = new ProposalsAdapter(context, proposals);
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
    }

    @OnClick(R.id.btnAdd)
    public void AddProposals(View view){
        Toast.makeText(context, "Añadira Propuestas", Toast.LENGTH_SHORT).show();
    }
}
