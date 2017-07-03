package com.xiberty.propongo.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = AppDatabase.class)
public class AttachmentDB extends BaseModel {

    public static String TAG = Council.class.getSimpleName();

    @PrimaryKey public int id;
    @Column public String name;
    @Column public String file;
    @Column  public int proposal;

}
