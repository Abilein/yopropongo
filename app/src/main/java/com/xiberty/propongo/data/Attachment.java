package com.xiberty.propongo.data;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;



@Table(database = AppDatabase.class)
public class Attachment {

    public static String TAG = Council.class.getSimpleName();

    @PrimaryKey public int id;
    @Column public String name;
    @Column public String file;
//    @ForeignKey public int proposal;

}
