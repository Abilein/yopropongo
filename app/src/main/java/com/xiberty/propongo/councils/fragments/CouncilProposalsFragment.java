package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.adapters.DirectiveAdapter;
import com.xiberty.propongo.councils.adapters.ProposalsAdapter;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CouncilProposalsFragment extends Fragment {
    private static final String TAG = CouncilProposalsFragment.class.getSimpleName();
    CouncilService mService;
    InboxPresenter presenter;
    View rootView = null;
    Context context;

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
        rootView = inflater.inflate(R.layout.fragment_council_proposals, container, false);
        ButterKnife.bind(this, rootView);

        context = rootView.getContext();
        Bundle bundle = getArguments();
        try{
            int CouncilManId = bundle.getInt("ID");
            CouncilMan councilmanSelected = CouncilMan.getCouncilman(context,CouncilManId);

            List<ProposalDB> proposals = SQLite.select().
                    from(ProposalDB.class).
                    where(ProposalDB_Table.id.is(CouncilManId)).
                    queryList();
            setProposals(proposals);
        }catch (Exception e){

        }

        return rootView;
    }

    private void setProposals(List<ProposalDB> proposals) {

        ProposalsAdapter adapter = new ProposalsAdapter(context, proposals);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        LinearLayout placeholder= (LinearLayout) rootView.findViewById(R.id.placeholder);
        TextView placeholder_text= (TextView) rootView.findViewById(R.id.placeholder_text);
        if (adapter.getCount()==0) {
            placeholder.setVisibility(View.VISIBLE);
            placeholder_text.setText("NO EXISTEN PROPUESTAS");
            listView.setVisibility(View.GONE);
        } else {
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
