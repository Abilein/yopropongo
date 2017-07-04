package com.xiberty.propongo.database;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xiberty.propongo.councils.models.DirectiveItem;

import java.util.ArrayList;


public class Council {

    @SerializedName("id") public int id;

     @SerializedName("name") public String name;
     @SerializedName("department") public String department;
     @SerializedName("creation_date") public String creation_date;
     @SerializedName("president") public int president;
     @SerializedName("vice_president") public int vice_president;
     @SerializedName("secretary") public int secretary;
     @SerializedName("vocal_a") public int vocal_a;
     @SerializedName("vocal_b") public int vocal_b;
     @SerializedName("logo") public String logo;

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

    public ArrayList<DirectiveItem> makedirective(Context context) {
        ArrayList<DirectiveItem> directive = new ArrayList<>();
        if (this.president != 0) {
            CouncilMan president = CouncilMan.getCouncilman(context,this.president);
            if (president != null)
                directive.add(new DirectiveItem(president, "presidente"));
        }
        if (vice_president != 0) {
            CouncilMan vicepresident = CouncilMan.getCouncilman(context,this.vice_president);
            if (vicepresident != null)
                directive.add(new DirectiveItem(vicepresident, "Vice Presidente"));
        }
        if (secretary != 0) {
            CouncilMan secretary = CouncilMan.getCouncilman(context,this.secretary);
            if (secretary != null)
                directive.add(new DirectiveItem(secretary, "Secretario"));
        }

        return directive;
    }


}
