package com.example.pruebatg1.dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pruebatg1.R;


public class PhotoShowDialog extends DialogFragment {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";

    private Context context;

    private ImageView imgPhoto;
    private TextView lblName;
    private TextView lblDescription;
    private Button btnAccept;

    private String name;
    private String description;
    private byte[] image;

    public PhotoShowDialog() {

    }

    public static PhotoShowDialog newInstance(String name, String description, byte[] image) {
        PhotoShowDialog fragment = new PhotoShowDialog();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(DESCRIPTION, description);
        args.putByteArray(IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(NAME);
            description = getArguments().getString(DESCRIPTION);
            image = getArguments().getByteArray(IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_photo_show, container, false);

        imgPhoto = view.findViewById(R.id.imgPhoto);
        lblName = view.findViewById(R.id.lblName);
        lblDescription = view.findViewById(R.id.lblDescription);
        btnAccept = view.findViewById(R.id.btnAccept);

        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        imgPhoto.setImageBitmap(bitmap);

        lblName.setText(name);
        lblDescription.setText(description);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
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
