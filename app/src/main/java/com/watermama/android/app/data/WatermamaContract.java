package com.watermama.android.app.data;

import android.net.Uri;
import android.content.ContentResolver;
import android.provider.BaseColumns;

public final class WatermamaContract {
    private WatermamaContract() {
    }
    public static final String CONTENT_AUTHORITY = "com.watermama.android.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RECORDS = "records";
    public static final class Entry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RECORDS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECORDS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECORDS;

        public final static String TABLE_NAME = "records";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_TIME = "time";
        public final static String COLUMN_VOLUME = "volume";
    }
}

