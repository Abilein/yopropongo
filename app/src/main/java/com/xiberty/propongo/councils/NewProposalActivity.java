package com.xiberty.propongo.councils;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewProposalActivity extends AppCompatActivity  implements NewProposalContract.View, AdapterView.OnItemSelectedListener {

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int councilId=0;
    private String commissionId="";
    private int for_cuncilman=0;
    private File proposal_file;
    private List<CouncilMan> councilsMenList = new ArrayList<>();

    NewProposalPresenter presenter;
    CouncilService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproposal);
        ButterKnife.bind(this);
        toolbar.setTitle("Nueva Propuesta");

        mService = WS.makeService(CouncilService.class,Store.getCredential(this));
        presenter = new NewProposalPresenter(this,mService);

        Council council = Store.getDefaultCouncil(this);
        councilId = council.id;

        //Filling the CouncilMen for Spinner
        councilsMenList = Store.getCouncilman(this);

        List<String> namesCouncilMen = new ArrayList<>();
        for (CouncilMan councilMan:councilsMenList){
            namesCouncilMen.add(councilMan.getFullName());
        }
        //Setup Spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, namesCouncilMen);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCouncilman.setAdapter(dataAdapter);
        spinnerCouncilman.setOnItemSelectedListener(this);

    }

    @OnClick(R.id.btn_add_attach)
    public void addAttach(View view){
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
    public void publishProposal(View view){
        String title = txtProposalTitle.getText().toString();
        String summary = txtProposalSummary.getText().toString();
        presenter.createProposal(this,title,summary,for_cuncilman,councilId,proposal_file);
    }

    /**
     * For Spinner of CouncilMan
     * **/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CouncilMan councilMan= councilsMenList.get(position);
        for_cuncilman = councilMan.getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showSuccessUploadProposal() {
        Toast.makeText(this, "Exito al crear Propuesta", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showErrorUploadProposal(String error) {
        Toast.makeText(this, "OMG! Error 'cause "+ error, Toast.LENGTH_SHORT).show();
    }
}
