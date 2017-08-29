package com.xiberty.propongo.councils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.MainActivity;
import com.xiberty.propongo.councils.adapters.SectionsPagerAdapter;
import com.xiberty.propongo.councils.fragments.BiographyFragment;
import com.xiberty.propongo.councils.fragments.DirectiveCommissionFragment;
import com.xiberty.propongo.councils.fragments.GeneralProposalsFragment;
import com.xiberty.propongo.councils.models.DirectiveItem;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommissionDetailActivity extends AppCompatActivity {

    @BindView(R.id.imgCover)
    ImageView imgCover;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.btnGoBack)
    LinearLayout btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commission_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int commissionID = bundle.getInt(Constants.KEY_COMMISSION_ID);
        Commission commissionSelected = Commission.getCommission(this, commissionID);
        if (commissionSelected != null) {
            if (commissionSelected.cover != null)
                Glide.with(this).load(commissionSelected.cover).into(imgCover);

            setTabs(commissionSelected);
        }

    }

    private void setTabs(Commission commissionSelected) {
        tabs.setBackgroundColor(Color.rgb(46,46,46));
        tabs.setTabTextColors(Color.WHITE, Color.rgb(254,190,17));
        tabs.setSelectedTabIndicatorColor(Color.rgb(254,190,17));
        setupViewPager(commissionSelected);
        tabs.setupWithViewPager(pager);

    }

    private void setupViewPager(Commission commissionSelected) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        DirectiveCommissionFragment directiveCommissionFragment = new DirectiveCommissionFragment();
        GeneralProposalsFragment generalProposalsFragment = new GeneralProposalsFragment();
        BiographyFragment biographyFragment = new BiographyFragment();

        ArrayList<DirectiveItem> directive = commissionSelected.makedirective(this);
        List<ProposalDB> proposals = SQLite.select().
                from(ProposalDB.class).
                where(ProposalDB_Table.commissions.is(commissionSelected.id +"")).
                queryList();

        Gson gson = new Gson();
        String directiveStr = gson.toJson(directive);
        String proposalStr = gson.toJson(proposals);

        Bundle bundle = new Bundle();
        bundle.putString("Directive", directiveStr);
        bundle.putString("Proposals", proposalStr);
        bundle.putString("About", commissionSelected.description);

        directiveCommissionFragment.setArguments(bundle);
        generalProposalsFragment.setArguments(bundle);
        biographyFragment.setArguments(bundle);

        adapter.addFragment(directiveCommissionFragment, "DIRECTIVA");
        adapter.addFragment(generalProposalsFragment, "PROPUESTAS");
        adapter.addFragment(biographyFragment, "ACERCA DE");
        pager.setAdapter(adapter);

    }

    @OnClick(R.id.btnGoBack)
    public void goBack(View view){
        onBackPressed();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CommissionDetailActivity.this, MainActivity.class);
        intent.putExtra(Constants.MENU_STATE, 4);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
