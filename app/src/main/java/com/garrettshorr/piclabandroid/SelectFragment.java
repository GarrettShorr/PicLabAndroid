package com.garrettshorr.piclabandroid;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


/**
 * Created by Garrett on 3/26/2016.
 */
public class SelectFragment extends Fragment {

    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_IMAGE_GALLERY = 1;

    private ImageButton selectImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        // --------------- Camera/Gallery Switch ---------------- //
        final Switch camGalSelect =
                (Switch) rootView.findViewById(R.id.select_switch_camera_gallery);
        camGalSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    camGalSelect.setText(getResources().getText(R.string.select_switch_on_text));
                else
                    camGalSelect.setText(getResources().getText(R.string.select_switch_off_text));
            }
        });

        // ---------- Select Image / Take Picture Button -------- //
        selectImage =
                (ImageButton) rootView.findViewById(R.id.select_imgbutton_select_picture);
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camGalSelect.isChecked()) {
                    //launch camera intent
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                    }
                } else {
                    //launch gallery intent
                    Intent choosePicture = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(choosePicture, REQUEST_IMAGE_GALLERY);
                }
            }
        });

        // -------------------- Filterize Button -------------------- //
        Button filterizeButton = (Button) rootView.findViewById(R.id.select_button_filterize);
        filterizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if default image has been changed to indicate selection has happened
                if (selectImage.getTag().equals("default"))
                    Toast.makeText(getActivity(), "Change Yo Image!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Image was changed, yo!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                setImageFromCamera(resultCode, data);
                break;

            case REQUEST_IMAGE_GALLERY:
                setImageFromGallery(resultCode, data);
                break;
        }
    }


    private void setImageFromGallery(int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver()
                    .query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imagePath = cursor.getString(columnIndex);
            cursor.close();
            selectImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            selectImage.setTag("fromGallery");
        }
    }


    private void setImageFromCamera(int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            Bitmap previewImage = (Bitmap) data.getExtras().get("data");
            selectImage.setImageBitmap(previewImage);
            selectImage.setTag("fromCamera");
        }
    }
/*
    private class PictureRetriever extends AsyncTask<Intent, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewWeakReference;
        private int requestCode;
        private final int data = 0;
        public PictureRetriever(ImageView imageView, int requestCode) {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
            this.requestCode = requestCode;
        }

        @Override
        protected Bitmap doInBackground(Intent... params) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:

                    break;

                case REQUEST_IMAGE_GALLERY:

                    break;
            }
            return null;
        }
    }
*/
}
