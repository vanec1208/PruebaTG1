package com.example.pruebatg1.dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pruebatg1.R;
import com.example.pruebatg1.db.PhotoController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PhotoNewDialog extends DialogFragment {
    private static final String PATH = "path";

    private Context context;

    private ImageView imgPhoto;
    private EditText txtName;
    private EditText txtDescription;
    private Button btnCancel;
    private Button btnAccept;

    private String path;

    public PhotoNewDialog() {

    }

    public static PhotoNewDialog newInstance(String path) {
        PhotoNewDialog fragment = new PhotoNewDialog();
        Bundle args = new Bundle();
        args.putString(PATH, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            path = getArguments().getString(PATH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_photo_new, container, false);

        imgPhoto = view.findViewById(R.id.imgPhoto);
        txtName = view.findViewById(R.id.txtName);
        txtDescription = view.findViewById(R.id.txtDescription);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnAccept = view.findViewById(R.id.btnAccept);

        try {
            File imageFile = new File(path);
            final Uri imageUri = Uri.fromFile(imageFile);
            final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            imgPhoto.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName = txtName.getText().toString();
                String strDescription = txtDescription.getText().toString();

                boolean isEmpty = false;

                if(TextUtils.isEmpty(strName)) {
                    txtName.setError(getString(R.string.empty_field));
                    isEmpty = true;
                }

                if(TextUtils.isEmpty(strDescription)) {
                    txtDescription.setError(getString(R.string.empty_field));
                    isEmpty = true;
                }

                if(isEmpty) return;

                try {
                    File imageFile = new File(path);
                    final Uri imageUri = Uri.fromFile(imageFile);
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    PhotoController.createPhoto(context, strName, strDescription, byteArray);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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
