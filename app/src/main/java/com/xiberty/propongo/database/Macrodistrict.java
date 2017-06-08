package com.xiberty.propongo.database;


import com.raizlabs.android.dbflow.annotation.*;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Macrodistrict extends BaseModel {
    public static final String TAG = Macrodistrict.class.getSimpleName();

    @PrimaryKey public int id;
    @Column private String name;
//    @ForeignKey private int user;


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
}
