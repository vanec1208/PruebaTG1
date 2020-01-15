package com.example.pruebatg1.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pruebatg1.R;
import com.example.pruebatg1.db.PhotoController;
import com.example.pruebatg1.util.Util;
import com.rw.loadingdialog.LoadingView;

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
    private byte[] byteArray = null;

    private LoadingView loadingView;

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

        loadingView = Util.getLoadingView(context);
        if(!TextUtils.isEmpty(path)) getByteArray();

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imgPhoto.setImageBitmap(bitmap);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strName = txtName.getText().toString();
                final String strDescription = txtDescription.getText().toString();

                boolean isEmpty = false;

                if (TextUtils.isEmpty(strName)) {
                    txtName.setError(getString(R.string.empty_field));
                    isEmpty = true;
                }

                if (TextUtils.isEmpty(strDescription)) {
                    txtDescription.setError(getString(R.string.empty_field));
                    isEmpty = true;
                }

                if (isEmpty) return;

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        PhotoController.createPhoto(context, strName, strDescription, byteArray);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismiss();
                            }
                        });
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
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

    public void getByteArray() {
        try {
            File imageFile = new File(path);
            final Uri imageUri = Uri.fromFile(imageFile);
            String realPath = Util.getRealPathFromURI(context, imageUri);

            final InputStream imageStream;
            imageStream = getActivity().getContentResolver().openInputStream(imageUri);

            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            double porcentajeHeight = 800.0 / bitmap.getHeight();
            int nuevoWidth = (int) (bitmap.getWidth() * porcentajeHeight);
            bitmap = Bitmap.createScaledBitmap(bitmap, nuevoWidth, 800, true);
            bitmap = Util.getRotatedBitmap(realPath, bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byteArray = stream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
