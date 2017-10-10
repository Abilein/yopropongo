package com.xiberty.propongo.accounts;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;


import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.Excepts;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.contrib.utils.ImageUtils;
import com.xiberty.propongo.credentials.responses.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;

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
        mView.showProgress();
        Call<UserProfile> request;
        RequestBody usernamePart = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody firstNamePart = RequestBody.create(MediaType.parse("text/plain"), firstName);
        RequestBody lastNamePart = RequestBody.create(MediaType.parse("text/plain"), lastName);

        Log.e("PERFIL", "PREPARANDO ACTUALIZACIÓN");
        if(uri != null) {


            File file = new File(ImageUtils.getPathFromUri(context, uri));
            String mediaType = context.getContentResolver().getType(uri);

            Log.e("PERFIL", "TIPO>>> " + mediaType + ", URI>> " + uri  + " FILE>>> " + file);


            RequestBody reqFile = RequestBody.create(
                    MediaType.parse(mediaType), file);


            MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo", file.getName(), reqFile);

            request = mService.updateProfileWithAvatar(
                    usernamePart, firstNamePart, lastNamePart, photoPart);

        } else {
            request = mService.updateProfile(usernamePart, firstNamePart, lastNamePart);
        }

        Log.e("PERFIL", "REQUEST PREPARADO" + request);

        request.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {


                if (response.isSuccessful()){

                    Log.e("PERFIL", "RESPUESTA BUENA");

                    UserProfile profile = response.body();
                    Store.saveProfile(context, profile);
                    mView.updateProfileSuccess(context.getString(R.string.success_account_updated));
                    mView.hideProgress();

                } else {

                    Log.e("PERFIL", "RESPUESTA MALA");

                    if(response.code()>=500 && response.code() <= 550) {
                        mView.updateProfileError(context.getString(R.string.error_server));
                        mView.hideProgress();
                    } else {
                        FormattedResp error = ParserError.parse(response);
                        String errorMessage = MessageManager.getMessage(context, error.code());
                        mView.updateProfileError(errorMessage);
                        mView.hideProgress();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
//                mView.hideProgress();
                if(Excepts.isConnectionFailure(t)) {
                    String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                    mView.updateProfileError(errorMessage);
                    mView.hideProgress();
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
