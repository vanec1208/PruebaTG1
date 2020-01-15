package com.example.pruebatg1.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esafirm.imagepicker.features.ImagePicker;
import com.example.pruebatg1.R;
import com.example.pruebatg1.dialogs.PhotoNewDialog;

public class MenuActivity extends AppCompatActivity {
    private int CODE_PERMISSION = 100;

    private Context context;

    private Button btnTakePhoto;
    private Button btnListPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        context = this;

        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnListPhotos = findViewById(R.id.btnListPhotos);

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MenuActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_PERMISSION);
                    return;
                }

                ImagePicker.cameraOnly().start(MenuActivity.this);
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == 100) {
                ImagePicker.cameraOnly().start(MenuActivity.this);
            } else {
                String path = ImagePicker.getFirstImageOrNull(data).getPath();
                PhotoNewDialog photoNewDialog = PhotoNewDialog.newInstance(path);
                photoNewDialog.show(getSupportFragmentManager(), "photoDialog");
            }
        }
    }
}
