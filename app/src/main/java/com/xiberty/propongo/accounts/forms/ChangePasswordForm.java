package com.xiberty.propongo.accounts.forms;


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
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.EditProfilePresenter;
import com.xiberty.propongo.contrib.views.XEditText;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordForm {
    @NotEmpty(messageResId= R.string.validation_required)
    @BindView(R.id.txtNewPassword)
    XEditText txtNewPassword;

    @NotEmpty(messageResId=R.string.validation_required)
    @BindView(R.id.txtCurrentPassword)
    XEditText txtCurrentPassword;

    @BindView(R.id.btnSavePassword)
    Button btnSavePassword;
    @BindView(R.id.btnClosePass)
    ImageView btnClosePass;

    private View view;
    private BottomSheetDialog sheet;
    private Context context;
    private EditProfilePresenter presenter;
    private Activity activity;

    public ChangePasswordForm(Context context, View view, BottomSheetDialog sheet, EditProfilePresenter presenter, Activity activity){
        this.view = view; this.sheet = sheet; this.context = context; this.presenter = presenter;
        ButterKnife.bind(this, view);
        this.sheet.setContentView (this.view);

        // Set sheet
        this.sheet.setCancelable (true);
        this.sheet.getWindow ().setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.sheet.getWindow ().setGravity (Gravity.BOTTOM);

        //Change behaviour when click to a EditText
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

    @OnClick(R.id.btnSavePassword)
    public void savePassword(){
        Validator validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {

                Log.e("DENTRO DE PASSWORD", "TODO VALIDO.....");
                presenter.changePasswordOnServer(context, txtCurrentPassword.getText().toString(),
                        txtNewPassword.getText().toString());

            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
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

    @OnClick(R.id.btnClosePass)
    public void closePass(){
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
