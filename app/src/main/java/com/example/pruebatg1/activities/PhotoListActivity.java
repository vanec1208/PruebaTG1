package com.example.pruebatg1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pruebatg1.R;
import com.example.pruebatg1.fragments.PhotoListFragment;

public class PhotoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        PhotoListFragment photoListFragment = new PhotoListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, photoListFragment).commit();
    }
}
