package com.xiberty.propongo.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by growcallisaya on 2/5/17.
 */

@Table(database = AppDatabase.class)
public class Council extends BaseModel{

    @PrimaryKey
    @SerializedName("id") @Expose public int id;

    @Column @SerializedName("name") @Expose public String name;
    @Column @SerializedName("department") @Expose public String department;
    @Column @SerializedName("creation_date") @Expose public String creation_date;
    @Column @SerializedName("president") @Expose public int president;
    @Column @SerializedName("vice_president") @Expose public int vice_president;
    @Column @SerializedName("secretary") @Expose public int secretary;
    @Column @SerializedName("vocal_a") @Expose public int vocal_a;
    @Column @SerializedName("vocal_b") @Expose public int vocal_b;
    @Column @SerializedName("logo") @Expose public String logo;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public int getPresident() {
        return president;
    }

    public int getVice_president() {
        return vice_president;
    }

    public int getSecretary() {
        return secretary;
    }

    public int getVocal_a() {
        return vocal_a;
    }

    public int getVocal_b() {
        return vocal_b;
    }

    public String getLogo() {
        return logo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setPresident(int president) {
        this.president = president;
    }

    public void setVice_president(int vice_president) {
        this.vice_president = vice_president;
    }

    public void setSecretary(int secretary) {
        this.secretary = secretary;
    }

    public void setVocal_a(int vocal_a) {
        this.vocal_a = vocal_a;
    }

    public void setVocal_b(int vocal_b) {
        this.vocal_b = vocal_b;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
