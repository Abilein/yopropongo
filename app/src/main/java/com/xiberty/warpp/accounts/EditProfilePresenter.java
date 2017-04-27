package com.xiberty.warpp.accounts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import com.xiberty.warpp.R;
import com.xiberty.warpp.contrib.Store;
import com.xiberty.warpp.contrib.api.Excepts;
import com.xiberty.warpp.contrib.api.FormattedResp;
import com.xiberty.warpp.contrib.api.MessageManager;
import com.xiberty.warpp.contrib.api.ParserError;
import com.xiberty.warpp.contrib.utils.ImageUtils;
import com.xiberty.warpp.credentials.responses.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfilePresenter implements EditProfileContract.EditProfilePresenterInterface {

    EditProfileContract.EditProfileView mView;

    private AccountService mService;
    private Bitmap imageBitmap = null;

    private ByteArrayOutputStream myImageByte;

    public EditProfilePresenter(EditProfileContract.EditProfileView mView, AccountService mService) {
        this.mView = mView;
        this.mService = mService;
    }

    @Override
    public void saveProfileOnServer(final Context context, String username,
                                    String firstName, String lastName, Uri uri) {

        Call<UserProfile> request;
        RequestBody usernamePart = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody firstNamePart = RequestBody.create(MediaType.parse("text/plain"), firstName);
        RequestBody lastNamePart = RequestBody.create(MediaType.parse("text/plain"), lastName);

        if(uri != null) {
            File file = new File(ImageUtils.getPathFromUri(context, uri));
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);

            request = mService.updateProfileWithAvatar(
                    usernamePart, firstNamePart, lastNamePart, photoPart);

        } else {
            request = mService.updateProfile(usernamePart, firstNamePart, lastNamePart);
        }

        request.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()){
                    UserProfile profile = response.body();
                    Store.saveProfile(context, profile);
                    mView.updateProfileSuccess(context.getString(R.string.success_account_updated));

                } else {
                    if(response.code()>=500 && response.code() <= 550) {
                        mView.updateProfileError(context.getString(R.string.error_server));
                    } else {
                        FormattedResp error = ParserError.parse(response);
                        String errorMessage = MessageManager.getMessage(context, error.code());
                        mView.updateProfileError(errorMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
//                mView.hideProgress();
                if(Excepts.isConnectionFailure(t)) {
                    String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                    mView.updateProfileError(errorMessage);
                }
            }
        });
    }

    @Override
    public void changePasswordOnServer(final Context context, String currentPass, String newPassword) {

        Log.e("CHANGE PASSWORD", "INSIDE");

        Call<FormattedResp> request = mService.changePassword(currentPass, newPassword);
        request.enqueue(new Callback<FormattedResp>() {
            @Override
            public void onResponse(Call<FormattedResp> call, Response<FormattedResp> response) {

                Log.e("CHANGE PASSWORD", "RESPONSE");

                if (response.isSuccessful()) {
                    FormattedResp resp = response.body();
                    String message = MessageManager.getMessage(context, resp.code());

                    Log.e("CHANGE PASSWORD", "SUCCESS");

                    mView.changePassSuccess(message);

                } else {
                    if(response.code()>=500 && response.code() <= 550) {

                        Log.e("CHANGE PASSWORD", "ERROR 500");

                        mView.updateProfileError(context.getString(R.string.error_server));
                    } else {

                        Log.e("CHANGE PASSWORD", "ERROR X");

                        FormattedResp error = ParserError.parse(response);
                        String errorMessage = MessageManager.getMessage(context, error.code());
                        mView.changePassError(errorMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<FormattedResp> call, Throwable t) {

                Log.e("CHANGE PASSWORD", "OTHER ERROR");

//                mView.hideProgress();
                if(Excepts.isConnectionFailure(t)) {
                    String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                    mView.changePassError(errorMessage);
                }
            }
        });
    }

    @Override
    public void changeEmailOnServer(final Context context, String password, String newEmail) {
        Call<FormattedResp> request = mService.changeEmail(password, newEmail);
        request.enqueue(new Callback<FormattedResp>() {
            @Override
            public void onResponse(Call<FormattedResp> call, Response<FormattedResp> response) {
                if (response.isSuccessful()) {
                    FormattedResp resp = response.body();
                    String message = MessageManager.getMessage(context, resp.code());
                    mView.changeEmailSuccess(message);

                } else {
                    if(response.code()>=500 && response.code() <= 550) {
                        mView.updateProfileError(context.getString(R.string.error_server));
                    } else {
                        FormattedResp error = ParserError.parse(response);
                        String errorMessage = MessageManager.getMessage(context, error.code());
                        mView.changeEmailError(errorMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<FormattedResp> call, Throwable t) {
//                mView.hideProgress();
                if(Excepts.isConnectionFailure(t)) {
                    String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                    mView.changeEmailError(errorMessage);
                }
            }
        });
    }

}
