package com.xiberty.propongo.councils;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.pchmn.materialchips.ChipsInput;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.utils.ActivityUtils;
import com.xiberty.propongo.councils.models.CouncilManChip;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.mateware.snacky.Snacky;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class NewProposalActivity extends AppCompatActivity implements NewProposalContract.View {

    private static final String TAG = NewProposalActivity.class.getSimpleName();

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
    @BindView(R.id.progressBarContent)
    RelativeLayout progressBarContent;

    private int councilId = 0;
    private File proposal_file;
    private List<CouncilMan> councilsMenList = new ArrayList<>();
    private String councilsMenIDs = "";

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


    /*
    *  PERMISSIONS
    * */
    @NeedsPermission(value = {Manifest.permission.READ_EXTERNAL_STORAGE})
    public void requestPermissionToReadFiles() {
    }

    @OnClick(R.id.btn_add_attach)
    public void addAttach(View view) {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        NewProposalActivityPermissionsDispatcher.requestPermissionToReadFilesWithCheck(this);
        if (ActivityUtils.hasPermissions(this, permissions)) {
            readFileFromStorage();
        } else {
            showInfo(getString(R.string.toast_permissions_denied_read));
        }
    }

    private void readFileFromStorage() {
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

    public void showInfo(String message) {
        Snacky.builder()
                .setActivty(NewProposalActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_LONG)
                .info()
                .show();
    }

    @OnClick(R.id.btnSendProposal)
    public void publishProposal(View view) {
        // get the list
        List<CouncilManChip> councilmenSelected = (List<CouncilManChip>) chipsInput.getSelectedChipList();
        for (CouncilManChip chip : councilmenSelected)
            councilsMenIDs += CouncilMan.getCouncilmanByName(context, chip.getLabel()) + ",";
        councilsMenIDs = councilsMenIDs.substring(0, councilsMenIDs.length() - 1);

        String title = txtProposalTitle.getText().toString();
        String description = txtProposalSummary.getText().toString();
        presenter.createProposal(this, title, description, councilsMenIDs, councilId, proposal_file);
    }

    @Override
    public void showSuccessUploadProposal() {
        Toast.makeText(this, "Exito al crear Propuesta, tu propuesta será REVISADA!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showErrorUploadProposal(String error) {
        Toast.makeText(this, "Ocurrio un error, vuelva intentarlo nás tarde", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "OMG! Error 'cause " + error);
    }

    @Override
    public void hideProgress() {
        progressBarContent.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBarContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        NewProposalActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
