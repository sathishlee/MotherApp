package com.unicef.thaimai.motherapp.helper;

import android.provider.BaseColumns;

/**
 * Created by Suthishan on 09-02-2018.
 * <p>
 * Constants for database column names
 */
public abstract class TableEntry implements BaseColumns {
    public static final String TABLE_NAME = "login";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PICMEID = "picmeid";
}

