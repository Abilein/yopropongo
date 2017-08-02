package com.xiberty.propongo.councils.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 2/8/17.
 */

 /*
    *    Example
    *     {
              "id": 1,
              "name": "Consejo Municipal de La Paz",
              "logo": "http://192.168.1.225:8000/media/councils/2017/07/27/Captura_de_pantalla_2017-07-27_a_las_17.40.28.png.100x100_q85_crop.png"
            }
    * */

public class Council_Proposal {

    @SerializedName("id")               public int id;
    @SerializedName("name")        public String name;
    @SerializedName("logo")            public String logo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
