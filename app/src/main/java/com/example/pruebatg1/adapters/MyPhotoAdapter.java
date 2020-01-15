package com.example.pruebatg1.adapters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pruebatg1.beans.Photo;
import com.example.pruebatg1.R;
import com.example.pruebatg1.dialogs.PhotoShowDialog;

import java.util.List;

public class MyPhotoAdapter extends RecyclerView.Adapter<MyPhotoAdapter.ViewHolder> {

    private Context context;
    private final List<Photo> listPhotos;

    public MyPhotoAdapter(Context context, List<Photo> listPhotos) {
        this.context = context;
        this.listPhotos = listPhotos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Photo photo = listPhotos.get(position);

        holder.lblName.setText(photo.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoShowDialog photoShowDialog = PhotoShowDialog.newInstance(photo.getName(),
                        photo.getDescription(), photo.getImage());
                photoShowDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "photoShow");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPhotos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView lblName;

        public ViewHolder(View view) {
            super(view);

            mView = view;
            lblName = view.findViewById(R.id.lblName);
        }
    }
}
