package com.xiberty.propongo.database;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.text.DecimalFormat;
import java.util.List;

@Table(database = AppDatabase.class)
public class ProposalDB extends BaseModel{

    public static final String TAG = ProposalDB.class.getSimpleName();

    @PrimaryKey public int id;
    @Column public String title;
    @Column public String description;
    @Column public String excerpt;
    @Column public int views;
    @Column public double average;
    @Column public int rate;
    @Column public String type;
    @Column public String status;
    @Column public String datetime;
    @Column public String commissions;
    @Column public String councilmen;
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

    public String getDescription() {
        return description;
    }

    public String getExcerpt() {
        return excerpt;
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

    public String getCommissions() {
        return commissions;
    }

    public String getCouncilmen() {
        return councilmen;
    }

    public int getCouncil() {
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

    public void setCommissions(String commissions) {
        this.commissions = commissions;
    }

    public void setCouncilmen(String councilmen) {
        this.councilmen = councilmen;
    }

    public void setCouncil(int council) {
        this.council = council;
    }

    public static ProposalDB getProposalById(int proposalID) {
        List<ProposalDB> proposalDB = SQLite.select().
                from(ProposalDB.class).
                where(ProposalDB_Table.id.is(proposalID)).
                queryList();
        if(!proposalDB.isEmpty())
            return proposalDB.get(0);
        return null;
    }
}
