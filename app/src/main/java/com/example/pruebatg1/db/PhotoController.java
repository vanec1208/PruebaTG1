package com.example.pruebatg1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.pruebatg1.beans.Photo;

import java.util.ArrayList;

public class PhotoController {

   public static void createPhoto(Context context, String name, String description, byte[] image){
       SQLiteDatabase db = TgDb.getInstance(context);

       ContentValues values = new ContentValues();
       values.put(TgTables.PhotoTable.NAME, name);
       values.put(TgTables.PhotoTable.DESCRIPTION, description);
       values.put(TgTables.PhotoTable.IMAGE, image);

        long insert = db.insert(TgTables.PhotoTable.TABLE_NAME, null, values);
        Log.d("getInsert", "Insert: " + insert);
   }

   public static ArrayList<Photo> getListPhotos(Context context){
       SQLiteDatabase db = TgDb.getInstance(context);

        ArrayList<Photo> listPhotos = new ArrayList<>();

        String columns [] = {TgTables.PhotoTable.NAME,
                TgTables.PhotoTable.DESCRIPTION,
                TgTables.PhotoTable.IMAGE};

        Cursor c = db.query(TgTables.PhotoTable.TABLE_NAME, columns,
                null, null, null, null, null);

        while (c.moveToNext()) {
            Photo photo = new Photo();
            photo.setName(c.getString(0));
            photo.setDescription(c.getString(1));
            photo.setImage(c.getBlob(2));
            listPhotos.add(photo);
        }

        return listPhotos;
   }
}
