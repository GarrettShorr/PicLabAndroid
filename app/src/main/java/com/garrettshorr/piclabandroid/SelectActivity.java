package com.garrettshorr.piclabandroid;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentByTag("SelectFragment") == null) {
            fm.beginTransaction()
                    .add(R.id.select_fragment_container, new SelectFragment(), "SelectFragment")
                    .commit();
        }
    }
}
