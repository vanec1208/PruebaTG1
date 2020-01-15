package com.example.pruebatg1.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pruebatg1.beans.Photo;
import com.example.pruebatg1.R;
import com.example.pruebatg1.adapters.MyPhotoAdapter;
import com.example.pruebatg1.db.PhotoController;

import java.util.ArrayList;

public class PhotoListFragment extends Fragment {

    private Context context;

    private ArrayList<Photo> listPhotos = new ArrayList<>();
    private RecyclerView recyclerView;
    private Button btnAccept;

    public PhotoListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        btnAccept = view.findViewById(R.id.btnAccept);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        listPhotos = PhotoController.getListPhotos(context);
        recyclerView.setAdapter(new MyPhotoAdapter(context, listPhotos));

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() != null) getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
