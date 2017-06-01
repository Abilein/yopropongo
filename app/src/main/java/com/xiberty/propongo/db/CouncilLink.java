package com.xiberty.propongo.db;


import com.raizlabs.android.dbflow.annotation.*;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class CouncilLink extends BaseModel{

    public static String TAG = CouncilLink.class.getSimpleName();

    @PrimaryKey public int id;
    @Column public String type;
    @Column public String url;
    @Column public int user;


}
