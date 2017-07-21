package com.xiberty.propongo.councils.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 7/7/17.
 */

public class ViewResponse {

    @SerializedName("id") public int id;
    @SerializedName("title") public String title;
    @SerializedName("views") public int views;
    @SerializedName("creation_date") public String creation_date;

}
