package com.garrettshorr.piclabandroid;

import android.content.Intent;
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
import android.widget.ToggleButton;


/**
 * Created by Garrett on 3/26/2016.
 */
public class SelectFragment extends Fragment {

    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_IMAGE_GALLERY = 1;

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
                if(b)
                    camGalSelect.setText(getResources().getText(R.string.select_switch_on_text));
                else
                    camGalSelect.setText(getResources().getText(R.string.select_switch_off_text));
            }
        });

        // ---------- Select Image / Take Picture Button -------- //
        final ImageButton selectImage =
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
                }
                else {
                    //launch gallery intent
                    Intent choosePicture = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(choosePicture, REQUEST_IMAGE_GALLERY);
                }
            }
        });



        return rootView;
    }
}