package com.xiberty.propongo.councils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.MainActivity;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.contrib.views.XTextView;
import com.xiberty.propongo.contrib.views.XTextViewBold;
import com.xiberty.propongo.councils.adapters.CommentAdapter;
import com.xiberty.propongo.councils.adapters.AttachmentAdapter;
import com.xiberty.propongo.councils.fragments.ProposalsFragment;
import com.xiberty.propongo.councils.models.DetailResponse;
import com.xiberty.propongo.database.AttachmentDB;
import com.xiberty.propongo.database.AttachmentDB_Table;
import com.xiberty.propongo.database.Comment;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class ProposalDetailActivity extends AppCompatActivity implements ProposalDetailContract.View, RatingBar.OnRatingBarChangeListener {

    private static final String TAG = ProposalDetailActivity.class.getSimpleName();
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.lblTitle)
    XTextViewBold lblTitle;
    @BindView(R.id.lblDate)
    TextView lblDate;
    @BindView(R.id.lblProposers)
    TextView lblProposers;
    @BindView(R.id.lblAverage)
    XTextViewBold lblAverage;
    @BindView(R.id.ratingAverage)
    RatingBar ratingAverage;
    @BindView(R.id.lblViewers)
    XTextView lblViewers;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.lblAttacchs)
    XTextView lblAttacchs;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.lblDescription)
    XTextView lblDescription;
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
    int proposalViews;

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
        setContent();

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
                lblDescription.setText(proposal.description);

                String [] CouncilmenIDs =proposal.councilmen.split(",");
                String CouncilmenNames="";
                for (String ID : CouncilmenIDs)
                    CouncilmenNames+= CouncilMan.getCouncilman(this, Integer.parseInt(ID)).getFullName()+", ";
                if(CouncilmenNames.length()>0)
                    CouncilmenNames = CouncilmenNames.substring(0,CouncilmenNames.length()-2);

                lblProposers.setText(CouncilmenNames);
                lblDate.setText(UIUtils.convertToDate(proposal.datetime));

                DecimalFormat df = new DecimalFormat("#.#");
                lblAverage.setText(String.valueOf(df.format(proposal.average)));
                ratingAverage.setRating(Float.parseFloat(String.valueOf(proposal.average)));

                lblAttacchs.setText(String.valueOf(attachments.size()));
                lblViewers.setText(String.valueOf(proposal.views));


                proposalId =String.valueOf(proposal.getId());
                proposalRate =proposal.getAverage();

                presenter.getComments(this, proposalId);
                presenter.getViews(this,proposalId);

                ratingAction.setStepSize(1);
                ratingAction.setRating(Float.parseFloat(String.valueOf(proposal.rate)));
                ratingAction.setOnRatingBarChangeListener(this);

                btnComments.setEnabled(false);
            }
            if (attachments.isEmpty())
                blockAttachs.setClickable(false);

        }
    }

    @Override
    public void showComments(List<Comment> comments) {
        /**
         * Show just 3 comments
         * **/
        if(comments.size()>3){
            btnComments.setEnabled(true);
        }
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
    }

    @Override
    public void showErrorComments(String error) {
//        Toasty.info(this, "Error al Cargar Comentarios | No existen Conetarios", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateRating(float average) {
        Toasty.success(this, "Gracias por Votar!", Toast.LENGTH_LONG, true).show();
        lblAverage.setText(String.valueOf(average));
        ratingAverage.setRating(average);
    }

    @Override
    public void errorRating(DetailResponse body) {
        Toast.makeText(context,"Error al rankear",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDetailResponse(String detail) {
        Toasty.success(context, detail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorToMakeComment(String error) {
        Toast.makeText(context,error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateViewers(int numberOfViews) {
        ProposalDB proposalDB = SQLite.select().
                from(ProposalDB.class)
                .where(ProposalDB_Table.id.is(Integer.parseInt(proposalId))).querySingle();
        proposalDB.setViews(numberOfViews);

        Log.e(TAG,String.valueOf(numberOfViews));
        lblViewers.setText(String.valueOf(numberOfViews));
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
        GoBackInBothCases();
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
                        if(input.toString().length()>0)
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
        int currentRate = (int)rating;
        presenter.rateProposal(this,proposalId,currentRate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GoBackInBothCases();
    }

    public void GoBackInBothCases(){
        Bundle bundle = getIntent().getExtras();
        String TAG = bundle.getString(Constants.KEY_BASE_CLASS);
        if (TAG.equals(ProposalsFragment.class.getSimpleName())){
            Intent intent = new Intent(ProposalDetailActivity.this, MainActivity.class);
            intent.putExtra(Constants.MENU_STATE,5);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else{
            finish();
        }
    }
}
