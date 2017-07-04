package com.xiberty.propongo.councils;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.database.ProposalDB;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProposalDetailActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.lblTitle)
    TextView lblTitle;
    @BindView(R.id.lblDate)
    TextView lblDate;
    @BindView(R.id.lblProposers)
    TextView lblProposers;
    @BindView(R.id.lblAverage)
    TextView lblAverage;
    @BindView(R.id.ratingAverage)
    RatingBar ratingAverage;
    @BindView(R.id.lblViewers)
    TextView lblViewers;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.lblAttacchs)
    TextView lblAttacchs;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.blockAttachs)
    LinearLayout blockAttachs;
    @BindView(R.id.lblDescription)
    TextView lblDescription;
    @BindView(R.id.ratingAction)
    RatingBar ratingAction;
    @BindView(R.id.listComments)
    ListView listComments;
    @BindView(R.id.btnComments)
    Button btnComments;
    @BindView(R.id.btnComment)
    FloatingActionButton btnComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            String proposalStr = bundle.getString(Constants.KEY_PROPOSAL_ID);
            Gson gson = new Gson();
            ProposalDB proposal = gson.fromJson(proposalStr, ProposalDB.class);
            if (proposal != null) {
                lblTitle.setText(proposal.title);
                lblDescription.setText(proposal.summary);

            }

        }
    }
}
