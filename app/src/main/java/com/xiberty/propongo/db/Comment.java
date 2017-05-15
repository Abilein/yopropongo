package com.xiberty.propongo.db;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = AppDatabase.class)
public class Comment extends BaseModel{

    public static final String TAG = Comment.class.getSimpleName();

    @PrimaryKey public int id;

    @Column public String content;
    @Column public String date;
    @Column public String full_name;
    @Column public String avatar;

}
