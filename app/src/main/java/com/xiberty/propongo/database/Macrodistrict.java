package com.xiberty.propongo.database;


import com.raizlabs.android.dbflow.annotation.*;
import com.raizlabs.android.dbflow.structure.BaseModel;

public class Macrodistrict  {
    public static final String TAG = Macrodistrict.class.getSimpleName();

    public int id;
    private String name;


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
