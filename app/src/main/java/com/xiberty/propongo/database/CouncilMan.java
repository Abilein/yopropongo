package com.xiberty.propongo.database;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.councils.CouncilManDetailActivity;

import java.util.List;


public class CouncilMan {

    /*
    *    Example
    *    {
    *       id (integer),
    *       first_name (string): Nombres de Concejal.,
    *       last_name (string): Apellidos del concejal,
    *       email (string): e.g victor@gmlp.gob.bo,
    *       avatar (string),
    *       bio (string): Escriba la biografia,
    *       town_council (string): Municipio,
    *       agrupation (string) = ['MAS' or 'SOL_BO' or 'UN']: Escoja su partido politico,
    *       links (string),
    *       macrodistrict {
    *         "id": 8,
    *         "name": "Zongo - Hampaturi"
    *       }
    *    }
    * */


    /**
     * ATTRIBUTES
     **/
    @SerializedName("id") public int id;
    @SerializedName("first_name") public String first_name;
    @SerializedName("last_name") public String last_name;
    @SerializedName("email") public String email;
    @SerializedName("avatar") public String avatar;
    @SerializedName("bio") public String bio;
    @SerializedName("town_council") public int town_council;
    @SerializedName("agrupation") public String agrupation;
    @SerializedName("macrodistrict") public Macrodistrict macrodistrict;
    @SerializedName("facebook") public String facebook;
    @SerializedName("twitter") public String twitter;

    /**
     * OBJECT GETTERS
     ***/
    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBio() {
        return bio;
    }

    public int getTown_council() {
        return town_council;
    }

    public String getAgrupation() {
        return agrupation;
    }

    public Macrodistrict getMacrodistrict() {
        return macrodistrict;
    }

    /**
     * OBJECT METHODS
     ***/
    public static final String MAS = "Movimiento al Socialismo";
    public static final String SOL = "Sol.bo";
    public static final String UN = "Unidad Nacional";

    public String getAgrupation(Context context){
        switch (this.agrupation){
            case MAS:
                return context.getString(R.string.group_mas);
            case SOL:
                return context.getString(R.string.group_sol);
            case UN:
                return context.getString(R.string.group_un);
            default:
                return null;
        }
    }


    public int getFlag(Context context){
        switch (this.agrupation){
            case MAS:
                return R.drawable.mas;
            case SOL:
                return R.drawable.solbo;
            case UN:
                return R.drawable.un;
            default:
                return R.drawable.ic_council;
        }
    }
    public String getFlagName(){
        switch (this.agrupation){
            case MAS:
                return "MAS";
            case SOL:
                return "SOL.BO";
            case UN:
                return "UNIDAD NACIONAL";
            default:
                return "Ninguno";
        }
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setTown_council(int town_council) {
        this.town_council = town_council;
    }

    public void setAgrupation(String agrupation) {
        this.agrupation = agrupation;
    }

    public void setMacrodistrict(Macrodistrict macrodistrict) {
        this.macrodistrict = macrodistrict;
    }


    public static CouncilMan getCouncilman(Context context, int ID) {
        List<CouncilMan> councilMen = Store.getCouncilman(context);
        for (CouncilMan councilMan: councilMen){
            if (councilMan.id == ID)
                return councilMan;
        }
        return null;
    }

    public String getFullName() {
        return first_name+" "+last_name;
    }

    public static String getCouncilmanByName(Context context, String full_name) {
        List<CouncilMan> councilMen = Store.getCouncilman(context);
        for (CouncilMan councilMan: councilMen){
            if (councilMan.getFullName().equals(full_name))
                return String.valueOf(councilMan.id);
        }
        return "";
    }
}
