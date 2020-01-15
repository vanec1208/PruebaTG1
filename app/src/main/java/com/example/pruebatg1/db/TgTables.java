package com.example.pruebatg1.db;

public class TgTables {

    public static abstract class PhotoTable {
        public static final String TABLE_NAME = "PhotoTable";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String IMAGE = "image";

        static final String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
                NAME + " TEXT PRIMARY KEY, " +
                DESCRIPTION + " TEXT, " +
                IMAGE + " BLOB " +
                ")";
    }
}
