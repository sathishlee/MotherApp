package com.unicef.thaimai.motherapp.Utility;

import android.provider.BaseColumns;

/**
 * Created by Suthishan on 19-02-2018.
 */
public abstract class TableEntry implements BaseColumns {
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PICME_ID = "picme_id";
}

