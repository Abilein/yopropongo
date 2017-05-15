package com.xiberty.propongo.council.responses;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.xiberty.propongo.R;

import java.util.ArrayList;

/**
 * Created by growcallisaya on 2/5/17.
 */

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
    *       macrodistrict (string)
    *    }
    * */


    /**
     * ATTRIBUTES
     **/
    @SerializedName("id") @Expose public int id;
    @SerializedName("first_name") @Expose public String first_name;
    @SerializedName("last_name") @Expose public String last_name;
    @SerializedName("email") @Expose public String email;
    @SerializedName("avatar") @Expose public String avatar;
    @SerializedName("bio") @Expose public String bio;
    @SerializedName("town_council") @Expose public int town_council;
    @SerializedName("agrupation") @Expose public String agrupation;
    @SerializedName("macrodistrict") @Expose public int macrodistrict;

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

    public int getMacrodistrict() {
        return macrodistrict;
    }

    /**
     * OBJECT METHODS
     ***/
    public static final String MAS = "MAS";
    public static final String SOL = "SOL_BO";
    public static final String UN = "UN";

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


}
