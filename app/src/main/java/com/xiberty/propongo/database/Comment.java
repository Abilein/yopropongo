package com.xiberty.propongo.database;


import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;



public class Comment {

    public static final String TAG = Comment.class.getSimpleName();

    @SerializedName("id")       public int id;
    @SerializedName("content")  public String content;
    @SerializedName("datetime")     public String datetime;
    @SerializedName("full_name")public String full_name;
    @SerializedName("photo")   public String photo;

}
