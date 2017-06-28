package com.xiberty.propongo.accounts.forms;


import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.EditProfileActivity;
import com.xiberty.propongo.accounts.MainActivity;
import com.xiberty.propongo.accounts.adapters.CouncilsSpinnerAdapter;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.database.Council;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;

public class CouncilForm {

    private View view;
    private BottomSheetDialog sheet;
    private MainActivity activity;
    private Council defaultCouncil;
    private List<Council> councils;
    private Context context;

    @BindView(R.id.spnCouncils) Spinner spnCouncils;
    @BindView(R.id.btnSelect) FancyButton btnSelect;

    public CouncilForm(MainActivity activity, View view, BottomSheetDialog sheet, List<Council> councils){
        this.view = view; this.sheet = sheet; this.activity = activity;
        ButterKnife.bind(this, view);
        context = view.getContext();

        this.sheet.setContentView (this.view);
        this.populateCouncils(councils);
        this.sheet.setCancelable (true);
        this.sheet.getWindow ().setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.sheet.getWindow ().setGravity (Gravity.BOTTOM);
    }

    public void populateCouncils(List<Council> items) {
        ArrayList<Council> councils = new ArrayList<Council>(items);
        this.councils = councils;
        spnCouncils.setAdapter(new CouncilsSpinnerAdapter(activity, councils));
        spnCouncils.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long idRow) {
                defaultCouncil = (Council) adapterView.getItemAtPosition(position);
                Store.setDefaultCouncil(activity, defaultCouncil);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(activity, "nothing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnSelect)
    public void selectCouncil(){
        activity.setCouncilinDrawer();
        sheet.dismiss();
    }

    public void show() {
        this.sheet.show();
    }
    public void hide() {
        this.sheet.dismiss();
    }
}
