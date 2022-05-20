package com.rofik.starterkit;
import android.os.Bundle;
import android.util.Log;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends Rofik {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String [] izin = {
                READ_EXTERNAL_STORAGE,
                CAMERA,
                ACCESS_COARSE_LOCATION
        };

        requestPermision("COba", 1, izin);

    }
}