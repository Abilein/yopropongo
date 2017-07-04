package com.xiberty.propongo.database;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.*;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

public class Proposal {

    public static final String TAG = Proposal.class.getSimpleName();

    @SerializedName("id") public int id;
    @SerializedName("title") public String title;
    @SerializedName("summary") public String summary;
    @SerializedName("commissions") public String commissions;
    @SerializedName("councilmen") public String councilmen;
    @SerializedName("views") public int views;
    @SerializedName("average") public double average;
    @SerializedName("rate") public int rate;
    @SerializedName("type") public String type;
    @SerializedName("attachments") public List<Attachment> attachments;
    @SerializedName("is_valid") public boolean is_valid;
    @SerializedName("creation_date") public String creation_date;
    @SerializedName("council") public int  council;



    public static String getTAG() {
        return TAG;
    }

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

    public double getAverage() {
        return average;
    }

    public int getRate() {
        return rate;
    }

    public String getType() {
        return type;
    }

    public boolean getIs_valid() {
        return is_valid;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

//SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCommissions(String commissions) {
        this.commissions = commissions;
    }

    public void setCouncilmen(String councilmen) {
        this.councilmen = councilmen;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

}
