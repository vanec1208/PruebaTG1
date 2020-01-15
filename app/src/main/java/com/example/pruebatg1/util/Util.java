package com.example.pruebatg1.util;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.example.pruebatg1.R;
import com.rw.loadingdialog.LoadingView;

import java.io.IOException;

public class Util {

    public static LoadingView getLoadingView(Context context) {
        LoadingView loadingView = new LoadingView.Builder(context)
                .setProgressColorResource(R.color.ef_black_alpha_50)
                .setBackgroundColorRes(R.color.colorTransparent)
                .setProgressStyle(LoadingView.ProgressStyle.CYCLIC)
                .setCustomMargins(0, 0, 0, 0)
                .attachTo((Activity) context);

        return loadingView;
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        if (DocumentsContract.isDocumentUri(context, contentURI)) {
            String filePath = "";

            String wholeID = DocumentsContract.getDocumentId(contentURI);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = 0;
            if (cursor != null)
                columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor != null && cursor.moveToFirst())
                filePath = cursor.getString(columnIndex);

            if (cursor != null)
                cursor.close();

            return filePath;
        } else {
            return contentURI.getPath();
        }
    }

    public static Bitmap getRotatedBitmap(String path, Bitmap bitmap) {
        try {
            ExifInterface exif = new ExifInterface(path);
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            Matrix matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);

            return rotatedBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int exifToDegrees(int exifOrientation) {
        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
        }

        return 0;
    }
}
