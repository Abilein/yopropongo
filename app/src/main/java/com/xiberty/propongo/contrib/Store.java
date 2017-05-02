package com.xiberty.propongo.contrib;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.contrib.api.OAuthCollection;
import com.xiberty.propongo.credentials.responses.OAuthCredential;
import com.xiberty.propongo.credentials.responses.UserProfile;

public class Store {


    private static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences(Constants.APPLICATION_PREFIX, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context){
        return Store.getPreferences(context).edit();
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = Store.getEditor(context);
        editor.putString(key, value);
        editor.commit();
    }


    public static void putInt(Context context, String tag, int value) {
        SharedPreferences.Editor editor = Store.getEditor(context);
        editor.putInt(tag, value);
        editor.commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = Store.getEditor(context);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = Store.getPreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = Store.getPreferences(context);
        return sharedPreferences.getInt(key, -1);
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = Store.getPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = Store.getEditor(context);
        editor.remove(key);
        editor.commit();
    }

    public static void clear(Context context){
        SharedPreferences.Editor editor = Store.getEditor(context);
        editor.clear();
        editor.commit();
    }


    /*
    *  CREDENTIALS MANAGEMENT
    * */
    public static boolean isLoggedIn(Context context) {
        String credential = Store.getString(context, Constants.OAUTH_CREDENTIAL);
        return (credential.length() > 0);
    }

    public static void saveCredential(Context context, OAuthCredential oAuth){
        Gson gson = new Gson();
        String oauthStr = gson.toJson(oAuth);
        Store.putString(context, Constants.OAUTH_CREDENTIAL, oauthStr);
    }

    public static OAuthCredential getCredential(Context context){
        Gson gson = new Gson();
        String oauthStr = Store.getString(context, Constants.OAUTH_CREDENTIAL);
        return gson.fromJson(oauthStr, OAuthCredential.class);
    }

    public static void removeCredential(Context context){
        Store.remove(context, Constants.OAUTH_CREDENTIAL);
    }


    /*
    *  ACCESS_TOKEN MANAGEMENT
    * */
    public static String getAccessToken(Context context) {
        OAuthCredential oAuth = getCredential(context);
        return oAuth.accessToken();
    }

    public static void saveOAuthCollection(Context context, OAuthCollection collection){
        Gson gson = new Gson();
        String collectionStr = gson.toJson(collection);
        Store.putString(context, Constants.CREDENTIAL_COLLECTION, collectionStr);
    }

    public static OAuthCollection getOAuthCollection(Context context){
        if(!hasOAuthCollection(context)){
            OAuthCollection oAuthCollection = new OAuthCollection();
            saveOAuthCollection(context, oAuthCollection);
            return oAuthCollection;
        } else {
            Gson gson = new Gson();
            String collectionStr = Store.getString(context, Constants.CREDENTIAL_COLLECTION);
            return gson.fromJson(collectionStr, OAuthCollection.class);
        }
    }

    public static void removeOAuthCollection(Context context){
        Store.remove(context, Constants.CREDENTIAL_COLLECTION);
    }

    private static boolean hasOAuthCollection(Context context) {
        String credential = Store.getString(context, Constants.CREDENTIAL_COLLECTION);
        return (credential.length() > 0);
    }


    /*
    *  PROFILE MANAGEMENT
    * */

    public static void saveProfile(Context context, UserProfile profile){
        Gson gson = new Gson();
        String profileStr = gson.toJson(profile);
        Store.putString(context, Constants.USER_PROFILE, profileStr);
    }

    public static UserProfile getProfile(Context context){
        Gson gson = new Gson();
        String profileStr = Store.getString(context, Constants.USER_PROFILE);
        return gson.fromJson(profileStr, UserProfile.class);
    }

    public static boolean hasProfile(Context context) {
        String profileStr = Store.getString(context, Constants.USER_PROFILE);
        return (profileStr.length() > 0);
    }

}

