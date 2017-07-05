package com.xiberty.propongo.councils;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.councils.adapters.CommentAdapter;
import com.xiberty.propongo.database.AttachmentDB;
import com.xiberty.propongo.database.AttachmentDB_Table;
import com.xiberty.propongo.database.Comment;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProposalDetailActivity extends AppCompatActivity implements ProposalDetailContract.View {

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

    ProposalDetailPresenter presenter;
    CouncilService ccService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_detail);
        ButterKnife.bind(this);
        ccService  = WS.makeService(CouncilService.class);
        presenter = new ProposalDetailPresenter(this,ccService);
        updateNumberOfViews();
        setContent();

    }

    private void updateNumberOfViews() {
        // Increase the Numbers of View
        // Update Database
        // Send the endpoint
    }

    private void setContent() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            String proposalStr = bundle.getString(Constants.KEY_PROPOSAL_ID);
            Gson gson = new Gson();
            ProposalDB proposal = gson.fromJson(proposalStr, ProposalDB.class);
            List<AttachmentDB> attachments = SQLite.select()
                    .from(AttachmentDB.class)
                    .where(AttachmentDB_Table.proposal.is(proposal.getId()))
                    .queryList();

            if (proposal != null) {
                lblTitle.setText(proposal.title);
                lblDescription.setText(proposal.summary);
                CouncilMan councilMan =CouncilMan.getCouncilman(this,Integer.parseInt(proposal.councilmen));
                lblProposers.setText(councilMan.getFullName());
                lblDate.setText(UIUtils.convertToDate(proposal.creation_date));
                lblAverage.setText(String.valueOf(proposal.average));
                lblViewers.setText(String.valueOf(proposal.views));
                lblAttacchs.setText(String.valueOf(attachments.size()));
                presenter.getComments(this,String.valueOf(proposal.getId()));
            }
        }
    }

    @Override
    public void showComments(List<Comment> comments) {
        /**
         * Show just 3 comments
         * **/
        List<Comment> threeComments = new ArrayList<>();
        int count = 0;
        for(Comment comment: comments){
            count++;
            threeComments.add(comment);
            if (count>3) break;
        }

        CommentAdapter adapter = new CommentAdapter(this, threeComments);
        listComments.setAdapter(adapter);
    }

    @Override
    public void showErrorComments(String error) {
        Toast.makeText(this, "Error al Cargar Comentarios | No existen Conetarios", Toast.LENGTH_SHORT).show();
    }
}
