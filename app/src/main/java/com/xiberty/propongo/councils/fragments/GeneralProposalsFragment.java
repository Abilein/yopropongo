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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.adapters.ProposalsAdapter;
import com.xiberty.propongo.database.ProposalDB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralProposalsFragment extends Fragment {
    private static final String TAG = GeneralProposalsFragment.class.getSimpleName();
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
        context = rootView.getContext();
        ButterKnife.bind(this, rootView);

        Bundle bundle = getArguments();
        if (!bundle.isEmpty()) {
            String proposalStr = bundle.getString("Proposals");
            Gson gson = new Gson();
            List<ProposalDB> proposals = gson.fromJson(proposalStr, new TypeToken<List<ProposalDB>>(){} .getType());
            setProposals(proposals);
        }

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
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
