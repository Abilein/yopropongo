package com.xiberty.propongo.database;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.*;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xiberty.propongo.councils.models.Commission_Proposal;
import com.xiberty.propongo.councils.models.Council_Proposal;
import com.xiberty.propongo.councils.models.Councilman_Proposal;

import java.util.List;

public class Proposal {

    public static final String TAG = Proposal.class.getSimpleName();

    @SerializedName("id") public int id;
    @SerializedName("title") public String title;
    @SerializedName("description") public String description;
    @SerializedName("excerpt") public String excerpt;
    @SerializedName("attachments") public List<Attachment> attachments;
    @SerializedName("views") public int views;
    @SerializedName("average") public double average;
    @SerializedName("rate") public int rate;
    @SerializedName("type") public String type;
    @SerializedName("status") public String status;
    @SerializedName("datetime") public String datetime;
    @SerializedName("commissions") public List<Commission_Proposal> commissions;
    @SerializedName("councilmen") public List<Councilman_Proposal> councilmen;
    @SerializedName("council") public Council_Proposal council;



    public static String getTAG() {
        return TAG;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public List<Attachment> getAttachments() {
        return attachments;
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

    public String getStatus() {
        return status;
    }

    public String getDatetime() {
        return datetime;
    }

    public List<Commission_Proposal> getCommissions() {
        return commissions;
    }

    public List<Councilman_Proposal> getCouncilmen() {
        return councilmen;
    }

    public Council_Proposal getCouncil() {
        return council;
    }

    //SETTERS


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setCommissions(List<Commission_Proposal> commissions) {
        this.commissions = commissions;
    }

    public void setCouncilmen(List<Councilman_Proposal> councilmen) {
        this.councilmen = councilmen;
    }

    public void setCouncil(Council_Proposal council) {
        this.council = council;
    }
}
