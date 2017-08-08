package com.xiberty.propongo.councils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.pchmn.materialchips.ChipsInput;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.councils.models.CouncilManChip;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewProposalActivity extends AppCompatActivity implements NewProposalContract.View {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtProposalTitle)
    EditText txtProposalTitle;
    @BindView(R.id.txtProposalSummary)
    EditText txtProposalSummary;
    @BindView(R.id.path_attach)
    TextView pathAttach;
    @BindView(R.id.scrollContent)
    ScrollView scrollContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.chips_input)
    ChipsInput chipsInput;

    private int councilId = 0;
    private File proposal_file;
    private List<CouncilMan> councilsMenList = new ArrayList<>();
    private String councilsMenIDs= "";

    NewProposalPresenter presenter;
    CouncilService mService;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproposal);
        ButterKnife.bind(this);
        toolbar.setTitle("Nueva Propuesta");

        context = getApplicationContext();

        mService = WS.makeService(CouncilService.class, Store.getCredential(this));
        presenter = new NewProposalPresenter(this, mService);

        Council council = Store.getDefaultCouncil(this);
        councilId = council.id;

        //Filling the CouncilMen for ChipsInput
        councilsMenList = Store.getCouncilman(this);

        List<CouncilManChip> contactList = new ArrayList<>();
        for (CouncilMan councilMan : councilsMenList) {
            contactList.add(new CouncilManChip(this, councilMan));
        }
        chipsInput.setFilterableList(contactList);

    }

    @OnClick(R.id.btn_add_attach)
    public void addAttach(View view) {
        String path = Environment.getExternalStorageDirectory().toString();
        new ChooserDialog().with(this)
                .withStartFile(path)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        proposal_file = pathFile;
                        Toast.makeText(NewProposalActivity.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                        pathAttach.setText(path);
                    }
                })
                .build()
                .show();
    }

    @OnClick(R.id.btnSendProposal)
    public void publishProposal(View view) {

        // get the list
        List<CouncilManChip> councilmenSelected = (List<CouncilManChip>) chipsInput.getSelectedChipList();
        for (CouncilManChip chip: councilmenSelected)
            councilsMenIDs+=CouncilMan.getCouncilmanByName(context,chip.getLabel())+",";
        councilsMenIDs= councilsMenIDs.substring(0,councilsMenIDs.length()-1);

        String title = txtProposalTitle.getText().toString();
        String description = txtProposalSummary.getText().toString();
        presenter.createProposal(this,title,description,councilsMenIDs,councilId,proposal_file);
    }

    @Override
    public void showSuccessUploadProposal() {
        Toast.makeText(this, "Exito al crear Propuesta, tu propuesta será REVISADA!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showErrorUploadProposal(String error) {
        Toast.makeText(this, "OMG! Error 'cause " + error, Toast.LENGTH_SHORT).show();
    }
}
