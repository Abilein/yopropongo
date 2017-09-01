package com.xiberty.propongo.councils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.views.XTextView;
import com.xiberty.propongo.contrib.views.XTextViewBold;
import com.xiberty.propongo.councils.adapters.AttachmentAdapter;
import com.xiberty.propongo.councils.fragments.ProposalsFragment;
import com.xiberty.propongo.councils.models.ActivateResponse;
import com.xiberty.propongo.councils.models.NewProposalRespse;
import com.xiberty.propongo.database.Attachment;
import com.xiberty.propongo.database.AttachmentDB;
import com.xiberty.propongo.database.Comment;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InboxDetailActivity extends AppCompatActivity {

    private static final String TAG = InboxDetailActivity.class.getSimpleName();
    ProposalDetailPresenter presenter;
    CouncilService ccService;
    List<Comment> comments;
    Context context;
    String proposalId;
    Double proposalRate;
    int proposalViews;


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.lblTitle)
    XTextViewBold lblTitle;
    @BindView(R.id.lblAttacchs)
    TextView lblAttacchs;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.lblDescription)
    XTextView lblDescription;
    @BindView(R.id.blockAttachs)
    LinearLayout blockAttachs;
    @BindView(R.id.imgBack)
    ImageView imgBack;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Attachment> attachments = null;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail);
        ButterKnife.bind(this);
        context = this;
        ccService = WS.makeService(CouncilService.class, Store.getCredential(context));
        setContent();
    }

    private void setContent() {
        Bundle bundle = getIntent().getExtras();
        if (!bundle.isEmpty()) {
            String proposalStr = bundle.getString(Constants.KEY_PROPOSAL_ID);
            Gson gson = new Gson();
            NewProposalRespse proposal = gson.fromJson(proposalStr, NewProposalRespse.class);

            Log.e(TAG,"PROPIESTA "+proposalStr);
            if (proposal != null) {
                lblTitle.setText(proposal.title);
                lblDescription.setText(proposal.description);
                attachments = proposal.attachments;

                if (attachments.size()>0 )
                    lblAttacchs.setText(String.valueOf(attachments.size()));
                else
                    blockAttachs.setClickable(false);

                proposalId = String.valueOf(proposal.id);
            }
        }
    }


    @OnClick(R.id.btnAccept)
    public void AcceptProposal(View view) {
        acceptEndpoint(proposalId);
        finish();
    }

    @OnClick(R.id.btnDeny)
    public void DenyProposal(View view) {
        Toast.makeText(context, "RECHAZADO", Toast.LENGTH_SHORT).show();
        denyEndpoint(proposalId);
        finish();
    }

    @OnClick(R.id.btnGoBack)
    public void goBack(View view) {
        GoBackInBothCases();
    }

    @OnClick(R.id.blockAttachs)
    public void viewFiles(View view) {
        List<AttachmentDB> attachmentDBs = new ArrayList<>();
        for (Attachment attachment :attachments){
            AttachmentDB a = new AttachmentDB();
            a.file = attachment.file;
            a.proposal = attachment.getId();
            a.name = attachment.getName();
            a.id = attachment.getId();
            attachmentDBs.add(a);
        }
        if (attachments != null) {
            new MaterialDialog.Builder(this)
                    .title(R.string.list_title)
                    .adapter(new AttachmentAdapter(this, attachmentDBs), null)
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GoBackInBothCases();
    }

    public void GoBackInBothCases() {
        Bundle bundle = getIntent().getExtras();
        String TAG = bundle.getString(Constants.KEY_BASE_CLASS);
        if (TAG.equals(ProposalsFragment.class.getSimpleName())) {
            Intent intent = new Intent(InboxDetailActivity.this, MainActivity.class);
            intent.putExtra(Constants.MENU_STATE, 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            finish();
        }
    }

    private void acceptEndpoint(String proposalID) {
        Call<ActivateResponse> activateCall = ccService.activeProposal(proposalID);
        activateCall.enqueue(new Callback<ActivateResponse>() {
            @Override
            public void onResponse(Call<ActivateResponse> call, Response<ActivateResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "PROPUESTA ACTIVADA!!", Toast.LENGTH_SHORT).show();
                }else{

                }
            }

            @Override
            public void onFailure(Call<ActivateResponse> call, Throwable t) {

            }
        });
    }

    private void denyEndpoint(String proposalID) {
        Call<ActivateResponse> activateCall = ccService.deleteProposal(proposalID);
        activateCall.enqueue(new Callback<ActivateResponse>() {
            @Override
            public void onResponse(Call<ActivateResponse> call, Response<ActivateResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, "PROPUESTA RECHAZADA!!", Toast.LENGTH_SHORT).show();
                }else{

                }
            }

            @Override
            public void onFailure(Call<ActivateResponse> call, Throwable t) {

            }
        });
    }
}
