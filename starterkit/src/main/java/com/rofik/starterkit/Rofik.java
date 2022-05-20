package com.rofik.starterkit;

import androidx.appcompat.app.AppCompatActivity;

import pub.devrel.easypermissions.EasyPermissions;

public class Rofik extends AppCompatActivity {

    public void requestPermision(String Pesan, int Kode, String... Permision){
        EasyPermissions.requestPermissions(this, Pesan, Kode, Permision);
    }

    public boolean hasPermision(String... Permision){
        return EasyPermissions.hasPermissions(this, Permision);
    }
}
