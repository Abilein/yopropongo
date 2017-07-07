package com.xiberty.propongo.councils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.councils.adapters.CommentAdapter;
import com.xiberty.propongo.councils.adapters.AttachmentAdapter;
import com.xiberty.propongo.councils.models.RateResponse;
import com.xiberty.propongo.database.AttachmentDB;
import com.xiberty.propongo.database.AttachmentDB_Table;
import com.xiberty.propongo.database.Comment;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ProposalDetailActivity extends AppCompatActivity implements ProposalDetailContract.View, RatingBar.OnRatingBarChangeListener {

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
    @BindView(R.id.lblDescription)
    TextView lblDescription;
    @BindView(R.id.ratingAction)
    RatingBar ratingAction;
    @BindView(R.id.listComments)
    ListView listComments;
    @BindView(R.id.blockAttachs)
    LinearLayout blockAttachs;
    @BindView(R.id.btnComments)
    Button btnComments;

    ProposalDetailPresenter presenter;
    CouncilService ccService;
    List<Comment> comments;
    Context context;
    String  proposalId;
    Double proposalRate;

    private List<AttachmentDB> attachments=null;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_detail);
        ButterKnife.bind(this);
        context = this;
        ccService = WS.makeService(CouncilService.class);
        presenter = new ProposalDetailPresenter(this, ccService);
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
            attachments = SQLite.select()
                    .from(AttachmentDB.class)
                    .where(AttachmentDB_Table.proposal.is(proposal.getId()))
                    .queryList();

            if (proposal != null) {
                lblTitle.setText(proposal.title);
                lblDescription.setText(proposal.summary);
                CouncilMan councilMan = CouncilMan.getCouncilman(this, Integer.parseInt(proposal.councilmen));
                lblProposers.setText(councilMan.getFullName());
                lblDate.setText(UIUtils.convertToDate(proposal.creation_date));
                lblAverage.setText(String.valueOf(proposal.average));
                lblViewers.setText(String.valueOf(proposal.views));
                lblAttacchs.setText(String.valueOf(attachments.size()));
                proposalId =String.valueOf(proposal.getId());
                proposalRate =proposal.getAverage();
                presenter.getComments(this, proposalId);
                ratingAction.setOnRatingBarChangeListener(this);
            }
            if (attachments.isEmpty())
                blockAttachs.setClickable(false);

        }
    }

    private void validateButtonComments() {
        if (this.comments.size()<4)
            btnComments.setEnabled(false);
    }

    @Override
    public void showComments(List<Comment> comments) {
        /**
         * Show just 3 comments
         * **/
        this.comments = comments;
        List<Comment> threeComments = new ArrayList<>();
        int count = 0;
        for (Comment comment : comments) {
            count++;
            threeComments.add(comment);
            if (count > 3) break;
        }

        CommentAdapter adapter = new CommentAdapter(this, threeComments);
        listComments.setAdapter(adapter);
        validateButtonComments();
    }

    @Override
    public void showErrorComments(String error) {
        Toast.makeText(this, "Error al Cargar Comentarios | No existen Conetarios", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateRating(String average) {
        Toast.makeText(context,"Exito",Toast.LENGTH_LONG).show();
        lblAverage.setText(average);
    }

    @Override
    public void errorRating(RateResponse body) {
        Toast.makeText(context,"Error al rankear",Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.btnComments)
    public void showMoreComments(View view) {
        Intent intent = new Intent(this, CommentsActivity.class);
        Gson gson = new Gson();
        String commentStr = gson.toJson(comments);
        intent.putExtra(Constants.KEY_COMMENTS, commentStr);
        startActivity(intent);
    }

    @OnClick(R.id.btnGoBack)
    public void goBack(View view) {
        finish();
    }


    @OnClick(R.id.floatBtnComment)
    public void commentNow(View view){
        new MaterialDialog.Builder(this)
                .title(R.string.comment_title)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Comentario", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        String comment = input.toString();

                        presenter.setComment(context,proposalId,comment);
                    }
                }).show();

    }
    @OnClick(R.id.blockAttachs)
    public void viewFiles(View view){
        if (attachments!=null){
            new MaterialDialog.Builder(this)
                    .title(R.string.list_title)
                    .adapter(new AttachmentAdapter(this,attachments), null)
                    .show();
        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        double currentRate = (double)rating;
        double averageRate = (currentRate + proposalRate)/2;
        presenter.rateProposal(this,proposalId,String.valueOf(averageRate));
    }
}
