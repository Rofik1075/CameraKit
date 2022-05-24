package com.rofik.starterkit;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fungsi helper = new Fungsi(this, this);

        String [] izin = {
                READ_EXTERNAL_STORAGE,
                CAMERA,
                ACCESS_COARSE_LOCATION
        };

        helper.requestPermision("COba", 1, izin);
    }
}