package com.xiberty.propongo.councils.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 2/8/17.
 */

 /*
    *    Example
    *    {
            "id": 1,
            "name": "Desarrollo Económico Financiero",
            "cover": "http://192.168.1.225:8000/media/covers/2017/07/27/Captura_de_pantalla_2017-07-27_a_las_18.03.21.png.100x300_q85_crop.png"
         }
    * */

public class Commission_Proposal {

    @SerializedName("id")               public int id;
    @SerializedName("name")             public String name;
    @SerializedName("cover")            public String cover;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
