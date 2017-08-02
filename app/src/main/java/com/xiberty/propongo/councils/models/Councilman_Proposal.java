package com.xiberty.propongo.councils.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by growcallisaya on 2/8/17.
 */

 /*
    *    Example
    *    {
            "id": 1,
            "full_name": "Pedro Susz",
            "email": "pedro.susz@gmlp.gob.bo",
            "avatar": "http://192.168.1.225:8000/media/concilmen/2017/07/27/Captura_de_pantalla_2017-07-27_a_las_17.38.45.png.100x100_q85_crop.png"
          }
    * */

public class Councilman_Proposal {

    @SerializedName("id")               public int id;
    @SerializedName("full_name")        public String full_name;
    @SerializedName("email")            public String email;
    @SerializedName("avatar")            public String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
