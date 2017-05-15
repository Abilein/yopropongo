package com.xiberty.propongo.data;

import com.raizlabs.android.dbflow.annotation.Database;
import com.xiberty.propongo.Constants;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = Constants.DATABASE_NAME;

    public static final int VERSION = Constants.DATABASE_VERSION;
}