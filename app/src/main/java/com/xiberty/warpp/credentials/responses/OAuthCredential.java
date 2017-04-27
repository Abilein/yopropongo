
package com.xiberty.warpp.credentials.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OAuthCredential {

    @SerializedName("access_token") @Expose  private String access_token;
    @SerializedName("token_type")   @Expose  private String token_type;
    @SerializedName("expires_in")   @Expose  private int expires_in;
    @SerializedName("scope")        @Expose  private String scope;
    @SerializedName("refresh_token")@Expose  private String refresh_token;


    public String accessToken() {
        return access_token;
    }
    public int expiresIn() {
        return expires_in;
    }
    public String refreshToken() {
        return refresh_token;
    }
    public String scope() {
        return scope;
    }
    public String tokenType() {
        return token_type;
    }
    public void accessToken(String access_token) {
        this.access_token = access_token;
    }
}

