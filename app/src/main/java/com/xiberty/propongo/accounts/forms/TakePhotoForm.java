package com.xiberty.propongo.accounts.forms;


import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.EditProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TakePhotoForm {

    private View view;
    private BottomSheetDialog sheet;
    private EditProfileActivity activity;
    public static final int PICTURE_FROM_CAMERA = 1;
    public static final int PICTURE_FROM_GALLERY = 2;

    @BindView(R.id.txtGallery) TextView txtGallery;
    @BindView(R.id.txtCamera) TextView txtCamera;

    public TakePhotoForm(EditProfileActivity activity, View view, BottomSheetDialog sheet){
        this.view = view; this.sheet = sheet; this.activity = activity;
        ButterKnife.bind(this, view);
        this.sheet.setContentView (this.view);

        this.sheet.setCancelable (true);
        this.sheet.getWindow ().setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.sheet.getWindow ().setGravity (Gravity.BOTTOM);
    }

    @OnClick(R.id.txtGallery)
    public void startGallery(){
        this.sheet.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, PICTURE_FROM_GALLERY);
    }

    @OnClick(R.id.txtCamera)
    public void startCamera(){
        this.sheet.dismiss();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, PICTURE_FROM_CAMERA);
        }
    }

    public void show() {
        this.sheet.show();
    }
    public void hide() {
        this.sheet.dismiss();
    }

}
