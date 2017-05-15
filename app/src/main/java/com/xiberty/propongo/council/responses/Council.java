package com.xiberty.propongo.council.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 2/5/17.
 */

public class Council {

    @SerializedName("id") @Expose public int id;
    @SerializedName("name") @Expose public String name;
    @SerializedName("department") @Expose public String department;
    @SerializedName("creation_date") @Expose public String creation_date;
    @SerializedName("president") @Expose public int president;
    @SerializedName("vice_president") @Expose public int vice_president;
    @SerializedName("secretary") @Expose public int secretary;
    @SerializedName("vocal_a") @Expose public int vocal_a;
    @SerializedName("vocal_b") @Expose public int vocal_b;
    @SerializedName("logo") @Expose public String logo;

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
}
