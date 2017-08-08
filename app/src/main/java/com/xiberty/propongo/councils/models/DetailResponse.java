package com.xiberty.propongo.councils.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 6/7/17.
 */

public class DetailResponse {

    @SerializedName("id") public int id;
    @SerializedName("datetime") public String datetime;
    @SerializedName("value") public int value;
    @SerializedName("proposal") public int proposal;
    @SerializedName("user") public int user;

}
