package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.adapters.InboxProposalsAdapter;
import com.xiberty.propongo.councils.models.NewProposalResponse;
import com.xiberty.propongo.database.ProposalDB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InboxFragment extends ToolbarBaseFragment implements InboxContract.CommissionView {
    private static final String TAG = InboxFragment.class.getSimpleName();
    CouncilService mService;
    InboxPresenter presenter;
    View rootView = null;
    Context context;

    @BindView(R.id.paddingBlock)
    FrameLayout paddingBlock;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.headerSubtitle)
    TextView headerSubtitle;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.placeholder_text)
    TextView placeholderText;
    @BindView(R.id.placeholder)
    LinearLayout placeholder;
    @BindView(R.id.progressBarContent)
    RelativeLayout progressBarContent;
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
        ButterKnife.bind(this, rootView);
        swipeContainer.setEnabled(false);

        //Toolbar
        setHeader(rootView, getString(R.string.menu_inbox).toUpperCase(), "CONCEJO MUNICIPAL DE LA PAZ");

        mService = WS.makeService(CouncilService.class);
        presenter = new InboxPresenter(mService, this);
        presenter.getInbox(context);
        return rootView;
    }

    private void setProposals(List<ProposalDB> proposals) {

    }

    @Override
    public void showProposals(List<NewProposalResponse> proposals) {
        InboxProposalsAdapter adapter = new InboxProposalsAdapter(context, proposals, TAG);
        if (adapter.getCount() == 0) {
            placeholder.setVisibility(View.VISIBLE);
            placeholderText.setText("NO EXISTEN PROPUESTAS EN TU BANDEJA DE ENTRADA");
            listView.setVisibility(View.GONE);
        } else {
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void showProgress() {
        progressBarContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarContent.setVisibility(View.GONE);
    }

    @Override
    public void showInboxError() {
        placeholder.setVisibility(View.VISIBLE);
        placeholderText.setText("NO EXISTEN PROPUESTAS EN TU BANDEJA DE ENTRADA");
        listView.setVisibility(View.GONE);
    }

}
