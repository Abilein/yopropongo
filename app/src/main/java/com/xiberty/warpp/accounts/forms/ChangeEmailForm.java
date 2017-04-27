package com.xiberty.warpp.accounts.forms;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.xiberty.warpp.R;
import com.xiberty.warpp.accounts.EditProfilePresenter;
import com.xiberty.warpp.contrib.views.XEditText;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeEmailForm {

    @NotEmpty(messageResId= R.string.validation_required)
    @BindView(R.id.txtPassword)
    XEditText txtPassword;

    @NotEmpty(messageResId=R.string.validation_required)
    @Email(messageResId=R.string.validation_email)
    @BindView(R.id.txtNewEmail)
    XEditText txtNewEmail;

    @BindView(R.id.btnSaveEmail) Button btnSaveEmail;
    @BindView(R.id.btnCloseEmail) ImageView btnCloseEmail;

    private View view;
    private BottomSheetDialog sheet;
    private Context context;
    private EditProfilePresenter presenter;
    private Activity activity;

    public ChangeEmailForm(Context context, View view, BottomSheetDialog sheet, EditProfilePresenter presenter,Activity activity){
        this.view = view; this.sheet = sheet; this.context = context; this.presenter = presenter;
        ButterKnife.bind(this, view);
        this.sheet.setContentView (this.view);

        this.sheet.setCancelable (true);
        this.sheet.getWindow ().setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.sheet.getWindow ().setGravity (Gravity.BOTTOM);


        //Readction on click on edtiText
        this.activity=activity;
        final FrameLayout bottomSheet = (FrameLayout) ((BottomSheetDialog) sheet).findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);

        KeyboardVisibilityEvent.setEventListener(this.activity, new KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
    }

    @OnClick(R.id.btnSaveEmail)
    public void saveEmail(){

        Validator validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                Log.e("DENTRO DE EMAIL", "TODO VALIDO.....");
                presenter.changeEmailOnServer(context, txtPassword.getText().toString(), txtNewEmail.getText().toString());
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                View view = null;
                for (ValidationError error : errors) {
                    view = error.getView();
                    String message = error.getCollatedErrorMessage(context);
                    if (view instanceof XEditText) {
                        final XEditText mView = ((XEditText) view);
                        mView.setError(message);

                        // Fix positioning error message
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Handler handler = new Handler();
                                final boolean b = handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() { mView.requestFocus(); }
                                }, 0);
                            }
                        });

                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        validator.validate();
    }


    @OnClick(R.id.btnCloseEmail)
    public void closeEmail(){
        this.sheet.dismiss();
    }

    public void show() {
        this.sheet.show();
    }
    public void hide() {
        this.sheet.dismiss();
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                dismiss();
                Log.e("ChangeEmail","dismiss()");
            }
        }
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
}
