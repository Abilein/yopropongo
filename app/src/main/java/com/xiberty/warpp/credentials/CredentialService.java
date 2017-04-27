package com.xiberty.warpp.credentials;

import com.xiberty.warpp.Constants;
import com.xiberty.warpp.contrib.api.FormattedResp;
import com.xiberty.warpp.credentials.responses.OAuthCredential;
import com.xiberty.warpp.credentials.responses.UserProfile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CredentialService {



    @FormUrlEncoded
    @POST(Constants.LOGIN_ENDPOINT)
    Call<OAuthCredential> getAccessToken(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("login") String login,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(Constants.RESET_PASSWORD_ENDPOINT)
    Call<FormattedResp> resetPassword (
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("email_or_username") String login
    );

    @FormUrlEncoded
    @POST(Constants.REGISTER_ENDPOINT)
    Call<UserProfile> register(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST(Constants.LOGOUT_ENDPOINT)
    Call<ResponseBody> logout(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("token") String username
    );


    @FormUrlEncoded
    @POST(Constants.FACEBOOK_LOGIN_ENDPOINT)
    Call<OAuthCredential> facebookLogin(
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret,
            @Field("access_token") String access_token
    );


}
