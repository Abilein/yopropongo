package com.xiberty.propongo.accounts;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.forms.ChangeEmailForm;
import com.xiberty.propongo.accounts.forms.ChangePasswordForm;
import com.xiberty.propongo.accounts.forms.TakePhotoForm;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.utils.ActivityUtils;
import com.xiberty.propongo.contrib.utils.ImageUtils;
import com.xiberty.propongo.contrib.views.XEditText;
import com.xiberty.propongo.councils.ProposalDetailActivity;
import com.xiberty.propongo.credentials.responses.UserProfile;

import android.widget.*;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.mateware.snacky.Snacky;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import android.support.v7.widget.Toolbar;


@RuntimePermissions
public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.EditProfileView{

    // Main form
    @BindView(R.id.imgAvatar) ImageView  imgAvatar;

    @NotEmpty(messageResId=R.string.validation_required)
    @Pattern(regex="[\\w.@+-]+", messageResId=R.string.validation_username)
    @BindView(R.id.txtUsername)
    XEditText txtUsername;

    @NotEmpty(messageResId=R.string.validation_required)
    @Length(min=1, max=30, messageResId=R.string.validation_name)
    @Pattern(regex="[a-z|A-Z|\\s]+", messageResId=R.string.validation_word)
    @BindView(R.id.txtFirstName)
    XEditText txtFirstName;

    @NotEmpty(messageResId=R.string.validation_required)
    @Length(min=1, max=30, messageResId=R.string.validation_name)
    @Pattern(regex="[a-z|A-Z|\\s]+", messageResId=R.string.validation_word)
    @BindView(R.id.txtLastName)
    XEditText txtLastName;


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.fabSave) FloatingActionButton fabSave;


    private boolean HAS_NEW_PHOTO = false;

    Bitmap imageBitmap = null;
    EditProfilePresenter presenter;
    AccountService service;
    ChangePasswordForm changePasswordForm;
    ChangeEmailForm changeEmailForm;
    TakePhotoForm takePhotoForm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);
        setToolbar();

        service = WS.makeService(AccountService.class, Store.getCredential(this));
        presenter = new EditProfilePresenter(this, service);
        setCurrentProfile();

    }

    @Override
    public void onResume(){
        super.onResume();
        setCurrentProfile();
    }


    public void setCurrentProfile(){
        UserProfile profile = Store.getProfile(getBaseContext());
        txtUsername.setText(profile.username());
        txtFirstName.setText(profile.firstName());
        txtLastName.setText(profile.lastName());

        if(!HAS_NEW_PHOTO) {
            if(profile.photo() != null && profile.photo().length() > 0){
                Glide.with(this)
                        .load(profile.photo())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgAvatar);
            } else {
                Glide.with(this)
                        .load(R.drawable.avatar)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgAvatar);
            }
        }
    }

    public void setToolbar() {
        toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_invisible));
        toolbar.setVisibility(android.view.View.VISIBLE);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.darken));
        setSupportActionBar(toolbar);
    }

    public void goToBack(android.view.View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_photo:
                editPhoto();
                break;
            case R.id.action_edit_password:
                editPassword();
                break;
            case R.id.action_edit_email:
                editEmail();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    @OnClick(R.id.fabSave)
    public void saveHandler() {

        Validator validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                if(HAS_NEW_PHOTO) {
                    Bitmap bitmap = ((BitmapDrawable)imgAvatar.getDrawable()).getBitmap();
                    Uri uri = ImageUtils.getUriFromBitmap(EditProfileActivity.this, bitmap);

                    presenter.saveProfileOnServer(
                            EditProfileActivity.this, txtUsername.getText().toString(),
                            txtFirstName.getText().toString(), txtLastName.getText().toString(), uri
                    );

                } else {
                    presenter.saveProfileOnServer(
                            EditProfileActivity.this, txtUsername.getText().toString(),
                            txtFirstName.getText().toString(), txtLastName.getText().toString(), null
                    );
                }
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(EditProfileActivity.this);
                    if (view instanceof XEditText) {
                        ((XEditText) view).setError(message);
                    } else {
                        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        validator.validate();
    }


    @Override
    public void editPassword() {
        View view = getLayoutInflater ().inflate (R.layout.sheet_edit_password, null);


        BottomSheetDialog sheet = new BottomSheetDialog (
                EditProfileActivity.this, R.style.Theme_Design_BottomSheetDialog);



        changePasswordForm = new ChangePasswordForm(this, view, sheet, presenter,EditProfileActivity.this);
        changePasswordForm.show();

    }

    @Override
    public void editEmail() {
        View view = getLayoutInflater ().inflate (R.layout.sheet_edit_email, null);
        BottomSheetDialog sheet = new BottomSheetDialog (
                EditProfileActivity.this, R.style.Theme_Design_BottomSheetDialog);

        changeEmailForm = new ChangeEmailForm(this, view, sheet, presenter,EditProfileActivity.this);
        changeEmailForm.show();

    }

    @Override
    public void editPhoto() {

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        EditProfileActivityPermissionsDispatcher.requestEditPhotoPermissionsWithCheck(this);
        if(ActivityUtils.hasPermissions(this, permissions)){

            View view = getLayoutInflater ().inflate (R.layout.sheet_edit_avatar, null);
            BottomSheetDialog sheet = new BottomSheetDialog (
                    EditProfileActivity.this, R.style.Theme_Design_BottomSheetDialog);

            takePhotoForm = new TakePhotoForm(this, view, sheet);
            takePhotoForm.show();

        } else {
            showInfo(getString(R.string.toast_permissions_denied));
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TakePhotoForm.PICTURE_FROM_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            try {
                imgAvatar.setImageBitmap(imageBitmap);
                HAS_NEW_PHOTO = true;
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (requestCode == TakePhotoForm.PICTURE_FROM_GALLERY && resultCode == RESULT_OK) {
            ImageUtils.setImageFromBitmapUri(getApplicationContext(), imageBitmap, imgAvatar, data.getData());
            HAS_NEW_PHOTO = true;
        }
    }


    @Override
    public void updateProfileSuccess(String message) {
        HAS_NEW_PHOTO = false;
        setCurrentProfile();
        successMessage(message);
    }

    public void successMessage(String message) {
        Snacky.builder()
            .setActivty(EditProfileActivity.this)
            .setText(message)
            .setDuration(Snacky.LENGTH_LONG)
            .success()
            .show();
    }

    @Override
    public void changeEmailSuccess(String message) {
        changeEmailForm.hide();
        successMessage(message);
    }

    @Override
    public void changeEmailError(String message) {
        changeEmailForm.hide();
        updateProfileError(message);
    }

    @Override
    public void changePassSuccess(String message) {
        changePasswordForm.hide();
        successMessage(message);

    }

    @Override
    public void changePassError(String message) {
        changePasswordForm.hide();
        updateProfileError(message);
    }

    @Override
    public void updateProfileError(String message) {
        HAS_NEW_PHOTO = false;
        Snacky.builder()
                .setActivty(EditProfileActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
    }

    public void showInfo(String message) {
        Snacky.builder()
                .setActivty(EditProfileActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_LONG)
                .info()
                .show();
    }


    /*
    *  PERMISSIONS
    * */
    @NeedsPermission(value={Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA})
    public void requestEditPhotoPermissions(){
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EditProfileActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        intent.putExtra(Constants.MENU_STATE,2);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
