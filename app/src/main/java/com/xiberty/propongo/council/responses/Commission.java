package com.xiberty.propongo.council.responses;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 2/5/17.
 */

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
    @SerializedName("id") @Expose               public int id;
    @SerializedName("name") @Expose             public String name;
    @SerializedName("description")@Expose       public String description;
    @SerializedName("creation_date")@Expose     public String creation_date;
    @SerializedName("cover")  @Expose           public String cover;
    @SerializedName("town_council")@Expose      public int town_council;
    @SerializedName("president")@Expose         public int president;
    @SerializedName("secretary")@Expose         public int secretary;
    @SerializedName("vocal")@Expose             public int vocal;

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
}
