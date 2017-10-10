package com.xiberty.propongo.database;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.councils.CommissionDetailActivity;
import com.xiberty.propongo.councils.models.DirectiveItem;

import java.util.ArrayList;
import java.util.List;


public class Commission {
    /*
    *    Example
    *    {
    *       "id": 1,
    *       "name": "Comision de Petroleo",
    *       "description": "Esta comisión esta encargada de los Petroles",
    *       "creation_date": "2017-04-17",
    *       "cover": "http://192.168.0.102:9000/media/covers/2017/04/17/fondoyp.jpg",
    *       "town_council": 1,
    *       "president": 1,
    *       "secretary": 1,
    *       "vocal": 1
    *    }
    * */
    /**
     * PROPERTIES
     **/
    @SerializedName("id")               public int id;
     @SerializedName("name")             public String name;
     @SerializedName("description")      public String description;
     @SerializedName("creation_date")    public String creation_date;
     @SerializedName("cover")            public String cover;
     @SerializedName("town_council")     public int town_council;
     @SerializedName("president")        public int president;
     @SerializedName("secretary")        public int secretary;
     @SerializedName("vocal")            public int vocal;

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String creation_date() {
        return creation_date;
    }

    public String cover() {
        return cover;
    }

    public String description() {
        return description;
    }

    public int town_council() {
        return town_council;
    }

    public int president() {
        return president;
    }

    public int secretary() {
        return secretary;
    }

    public int vocal() {
        return vocal;
    }

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }


//    Setters


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setTown_council(int town_council) {
        this.town_council = town_council;
    }

    public void setPresident(int president) {
        this.president = president;
    }

    public void setSecretary(int secretary) {
        this.secretary = secretary;
    }

    public void setVocal(int vocal) {
        this.vocal = vocal;
    }

    public ArrayList<DirectiveItem> makedirective(Context context) {
        ArrayList<DirectiveItem> directive = new ArrayList<>();
        if (this.president != 0) {
            CouncilMan president = CouncilMan.getCouncilman(context,this.president);
            if (president != null)
                directive.add(new DirectiveItem(president, "PRESIDENTE"));
        }
        if (this.secretary != 0) {
            CouncilMan secretary = CouncilMan.getCouncilman(context,this.secretary);
            if (secretary != null)
                directive.add(new DirectiveItem(secretary, "SECRETARIO"));
        }
        if (this.vocal != 0) {
            CouncilMan vocal = CouncilMan.getCouncilman(context,this.vocal);
            if (vocal != null)
                directive.add(new DirectiveItem(vocal, "VOCAL"));
        }

        return directive;
    }

    public static Commission getCommission(Context context, int commissionID) {
        List<Commission> commissions = Store.getCommissions(context);
        for (Commission commission : commissions){
            if (commission.id == commissionID)
                return commission;
        }
        return null;
    }
}
