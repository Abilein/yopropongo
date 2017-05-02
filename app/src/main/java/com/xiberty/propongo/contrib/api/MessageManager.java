package com.xiberty.propongo.contrib.api;


import android.content.Context;

import com.xiberty.propongo.R;

public class MessageManager {

    public static final String WITHOUT_CONNECTION = "without_connection";
    public static final String INVALID_APPLICATION = "invalid_application";
    public static final String INVALID_ACCESS_TOKEN = "invalid_access_token";
    public static final String INVALID_CREDENTIALS = "invalid_credentials";
    public static final String INVALID_PASSWORD = "invalid_password";
    public static final String RESET_EMAIL_SENT = "reset_email_sent";
    public static final String USER_NOT_FOUND = "user_not_found";
    public static final String USED_EMAIL = "used_email";
    public static final String USED_USERNAME = "used_username";
    public static final String USED_USERNAME_EMAIL = "used_username_email";
    public static final String EMAIL_UPDATED = "email_updated";
    public static final String EMAIL_WITHOUT_CHANGES = "email_without_changes";
    public static final String PASSWORD_CHANGED = "password_changed";
    public static final String MALFORMED_NEW_PASSWORD = "malformed_new_password";


    public static String getMessage(Context context, String code){

        switch (code){
            case WITHOUT_CONNECTION:
                return context.getString(R.string.error_without_connection);
            case INVALID_APPLICATION:
                return context.getString(R.string.error_invalid_application);

            case INVALID_CREDENTIALS:
                return context.getString(R.string.error_invalid_credentials);

            case INVALID_PASSWORD:
                return context.getString(R.string.error_invalid_password);

            case RESET_EMAIL_SENT:
                return context.getString(R.string.success_reset_email_sent);

            case USER_NOT_FOUND:
                return context.getString(R.string.error_user_not_found);

            case USED_EMAIL:
                return context.getString(R.string.error_used_email);

            case USED_USERNAME:
                return context.getString(R.string.error_used_username);

            case USED_USERNAME_EMAIL:
                return context.getString(R.string.error_used_username_email);

            case EMAIL_UPDATED:
                return context.getString(R.string.success_email_updated);

            case EMAIL_WITHOUT_CHANGES:
                return context.getString(R.string.success_email_without_changes);

            case PASSWORD_CHANGED:
                return context.getString(R.string.success_password_changed);

            case MALFORMED_NEW_PASSWORD:
                return context.getString(R.string.error_malformed_new_password);

            case INVALID_ACCESS_TOKEN:
                return context.getString(R.string.error_invalid_access_token);


            default:
                return context.getString(R.string.error_default);
        }
    }

}
