package com.xiberty.propongo.council.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 2/5/17.
 */

public class Proposal {

    /**
     * PROPERTIES
     **/
    @SerializedName("id") @Expose public int id;
    @SerializedName("title") @Expose public String title;
    @SerializedName("summary") @Expose public String summary;
    @SerializedName("commissions") @Expose public String commissions;
    @SerializedName("councilmen") @Expose public String councilmen;
    @SerializedName("views") @Expose public int views;
    @SerializedName("attachs") @Expose public int attachs;
    @SerializedName("average") @Expose public double average;
    @SerializedName("rate") @Expose public int rate;
    @SerializedName("isValid") @Expose public int isValid;
    @SerializedName("date") @Expose public String date;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getCommissions() {
        return commissions;
    }

    public String getCouncilmen() {
        return councilmen;
    }

    public int getViews() {
        return views;
    }

    public int getAttachs() {
        return attachs;
    }

    public double getAverage() {
        return average;
    }

    public int getRate() {
        return rate;
    }

    public int getIsValid() {
        return isValid;
    }

    public String getDate() {
        return date;
    }
}
