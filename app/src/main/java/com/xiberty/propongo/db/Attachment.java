package com.xiberty.propongo.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = AppDatabase.class)
public class Attachment extends BaseModel {

    public static String TAG = Council.class.getSimpleName();

    @PrimaryKey public int id;
    @Column public String name;
    @Column public String file;
    @ForeignKey public int proposal;

}
