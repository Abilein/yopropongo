package com.xiberty.propongo.database;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class ProposalDB extends BaseModel{

    public static final String TAG = ProposalDB.class.getSimpleName();

    @PrimaryKey public int id;
    @Column public String title;
    @Column public String summary;
    @Column public String commissions;
    @Column public String councilmen;
    @Column public int views;
    @Column public double average;
    @Column public int rate;
    @Column public String type;
    @Column public boolean is_valid;
    @Column public String creation_date;
    @Column public int council;

    //GETTERS

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
