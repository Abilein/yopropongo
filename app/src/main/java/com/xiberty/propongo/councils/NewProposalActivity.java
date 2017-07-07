package com.xiberty.propongo.councils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.xiberty.propongo.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewProposalActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtProposalTitle)
    EditText txtProposalTitle;
    @BindView(R.id.txtProposalSummary)
    EditText txtProposalSummary;
    @BindView(R.id.spinnerCouncilman)
    Spinner spinnerCouncilman;
    @BindView(R.id.txtComissions)
    TextView txtComissions;
    @BindView(R.id.path_attach)
    TextView pathAttach;
    @BindView(R.id.scrollContent)
    ScrollView scrollContent;
    @BindView(R.id.btnSendProposal)
    Button btnSendProposal;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproposal);
        ButterKnife.bind(this);
        toolbar.setTitle("Nueva Propuesta");
        
    }

}
