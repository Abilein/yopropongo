package com.xiberty.propongo.councils.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 8/7/17.
 */

public class ReceiverResponse {
    @SerializedName("id") public int id;
    @SerializedName("full_name") public String full_name;
    @SerializedName("email") public String email;
    @SerializedName("avatar") public String avatar;
}
