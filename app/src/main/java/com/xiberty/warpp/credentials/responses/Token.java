
package com.xiberty.warpp.credentials.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Token {
    /*
    *   Example:
    *   {
    *       "id": 1,
    *       "creation_date": "2016-11-16T18:46:41.655450Z",
    *       "code": "8805acfa-725a-47c2-a563-5f5c48ff4cbd",
    *       "status": "PENDING",
    *       "user": 1
    *   }
    *
    * */

    @SerializedName("id") @Expose private int id;
    @SerializedName("creation_date") @Expose private String creation_date;
    @SerializedName("code")   @Expose private String code;
    @SerializedName("status")  @Expose private String status;
    @SerializedName("user")@Expose private int user;


    public String getCode() {
        return code;
    }
    public String getCreation_date() {
        return creation_date;
    }
    public int getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public int getUser() {
        return user;
    }

}

