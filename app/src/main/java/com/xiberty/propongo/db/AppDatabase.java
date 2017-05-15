package com.xiberty.propongo.db;

import com.raizlabs.android.dbflow.annotation.Database;
import com.xiberty.propongo.Constants;

/**
 * Created by growcallisaya on 4/5/17.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = Constants.DATABASE_NAME; // we will add the .db extension

    public static final int VERSION = Constants.DATABASE_VERSION;
}
