package com.xiberty.propongo.accounts;

import android.content.Context;
import android.net.Uri;


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


        void hideProgress();
        void showProgress();
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
