package com.xiberty.warpp.accounts;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.xiberty.warpp.credentials.responses.UserProfile;


public class EditProfileContract {
    public interface EditProfileView{

        void saveHandler();
        void editPassword();
        void editEmail();
        void editPhoto();

        void updateProfileSuccess(String message);
        void updateProfileError(String message);

        void changeEmailSuccess(String message);
        void changeEmailError(String message);

        void changePassSuccess(String message);
        void changePassError(String message);


    }

    public interface EditProfilePresenterInterface{
        void saveProfileOnServer(Context context, String username,
                                 String firstName, String lastName, Uri uri);
        void changePasswordOnServer(Context context,
                                    String currentPass, String newPassword);
        void changeEmailOnServer(Context context,
                                 String password, String newEmail);

    }
}
