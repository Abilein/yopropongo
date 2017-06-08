package com.xiberty.propongo.database;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Council extends BaseModel{

    @PrimaryKey
    @SerializedName("id") public int id;

    @Column @SerializedName("name") public String name;
    @Column @SerializedName("department") public String department;
    @Column @SerializedName("creation_date") public String creation_date;
    @Column @SerializedName("president") public int president;
    @Column @SerializedName("vice_president") public int vice_president;
    @Column @SerializedName("secretary") public int secretary;
    @Column @SerializedName("vocal_a") public int vocal_a;
    @Column @SerializedName("vocal_b") public int vocal_b;
    @Column @SerializedName("logo") public String logo;

    public int id() {return id;}
    public String name() {
        return name;
    }
    public String department() {
        return department;
    }
    public String creationDate() {
        return creation_date;
    }
    public int president() {
        return president;
    }
    public int vicePresident() {
        return vice_president;
    }
    public int secretary() {
        return secretary;
    }
    public int vocalA() {
        return vocal_a;
    }
    public int vocalB() {
        return vocal_b;
    }
    public String logo() {
        return logo;
    }

    public void id(int id) {
        this.id = id;
    }
    public void name(String name) {
        this.name = name;
    }
    public void department(String department) {
        this.department = department;
    }
    public void creationDate(String creation_date) {
        this.creation_date = creation_date;
    }
    public void president(int president) {
        this.president = president;
    }
    public void vicePresident(int vice_president) {
        this.vice_president = vice_president;
    }
    public void secretary(int secretary) {this.secretary = secretary;}
    public void vocalA(int vocal_a) {
        this.vocal_a = vocal_a;
    }
    public void vocalB(int vocal_b) {
        this.vocal_b = vocal_b;
    }
    public void logo(String logo) {
        this.logo = logo;
    }


    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
