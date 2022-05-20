package com.rofik.starterkit;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Rofik rofik = new Rofik(this, this);

        String [] izin = {
                READ_EXTERNAL_STORAGE,
                CAMERA,
                ACCESS_COARSE_LOCATION
        };

        rofik.requestPermision("COba", 1, izin);
    }
}