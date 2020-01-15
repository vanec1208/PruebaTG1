package com.example.pruebatg1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TgDb {
    private static SQLiteDatabase INSTANCE = null;

    private synchronized static void createInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TgHelper(context).getReadableDatabase();
        }
    }

    public static SQLiteDatabase getInstance(Context context) {
        if (INSTANCE == null) createInstance(context);
        return INSTANCE;
    }

}
