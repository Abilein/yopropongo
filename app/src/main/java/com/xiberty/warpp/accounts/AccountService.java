package com.xiberty.warpp.accounts;


import com.google.gson.annotations.JsonAdapter;
import com.xiberty.warpp.Constants;
import com.xiberty.warpp.contrib.api.FormattedResp;
import com.xiberty.warpp.credentials.responses.UserProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface AccountService {



    @GET(Constants.PROFILE_ENDPOINT)
    Call<UserProfile> getProfile();

    @Multipart
    @PUT(Constants.PROFILE_ENDPOINT)
    Call<UserProfile> updateProfile(
            @Part("username") RequestBody username,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name);

    @Multipart
    @PUT(Constants.PROFILE_ENDPOINT)
    Call<UserProfile> updateProfileWithAvatar(
            @Part("username") RequestBody username,
            @Part("first_name") RequestBody first_name,
            @Part("last_name") RequestBody last_name,
            @Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST(Constants.CHANGE_EMAIL_ENDPOINT)
    Call<FormattedResp> changeEmail(
            @Field("password") String password,
            @Field("email") String email);

    @FormUrlEncoded
    @POST(Constants.CHANGE_PASSWORD_ENDPOINT)
    Call<FormattedResp> changePassword(
            @Field("password") String password,
            @Field("new_password") String new_password);



}
