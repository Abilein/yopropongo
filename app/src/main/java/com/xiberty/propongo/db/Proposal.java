package com.xiberty.propongo.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.*;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Proposal extends BaseModel{

    public static final String TAG = Proposal.class.getSimpleName();

    @PrimaryKey @SerializedName("id") public int id;
    @Column @SerializedName("title") public String title;
    @Column @SerializedName("summary") public String summary;
    @Column @SerializedName("commissions") public String commissions;
    @Column @SerializedName("councilmen") public String councilmen;
    @Column @SerializedName("views") public int views;
    @Column @SerializedName("attachs") public int attachs;
    @Column @SerializedName("average") public double average;
    @Column @SerializedName("rate") public int rate;

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

    public int getAttachs() {
        return attachs;
    }

    public double getAverage() {
        return average;
    }

    public int getRate() {
        return rate;
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

    public void setAttachs(int attachs) {
        this.attachs = attachs;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
