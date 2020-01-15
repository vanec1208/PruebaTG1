package com.example.pruebatg1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.pruebatg1.R;
import com.example.pruebatg1.fragments.PhotoListFragment;
import com.example.pruebatg1.util.Util;
import com.rw.loadingdialog.LoadingView;

public class PhotoListActivity extends AppCompatActivity {
    private Context context;

    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        context = this;

        loadingView = Util.getLoadingView(context);

        PhotoListFragment photoListFragment = new PhotoListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, photoListFragment).commit();
    }
}
