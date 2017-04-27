
package com.xiberty.warpp.credentials.responses;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserProfile {
    /*
    *    Example
    *    {
    *        "id": 6,
    *        "username": "xiberty",
    *        "first_name": "Xiberty",
    *        "last_name": "LAB",
    *        "email": "info@xiberty.com",
    *        "photo": "http://xiberty.com/media/users/2016/12/06/Warpp_facebookProfileBIG.jpg"
    *    }
    * */

    @SerializedName("id") @Expose               private int      id;
    @SerializedName("username") @Expose         private String   username;
    @SerializedName("first_name")   @Expose     private String   first_name;
    @SerializedName("last_name")  @Expose       private String   last_name;
    @SerializedName("email")@Expose             private String   email;
    @SerializedName("photo")@Expose             private String   photo;

    public int id() { return id; }

    public String username() {return username;}

    public String firstName() {return first_name;}

    public String lastName() {return last_name;}

    public String email() {return email;}

    public String photo() {return photo;}


    public String fullName(){
        if(first_name!=null && last_name!=null && first_name.length() > 0 && last_name.length() > 0 ) {
            return first_name + " " + last_name;
        } else {
            return username;
        }
    }

    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

