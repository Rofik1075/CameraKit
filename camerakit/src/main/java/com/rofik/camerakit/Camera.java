package com.rofik.camerakit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.IOException;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class Camera {
    Context context;
    Activity activity;
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_GALERI_PERM = 124;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALERY = 2;

    public Camera(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void requestCamera(){
        if (hasCameraPermission()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            EasyPermissions.requestPermissions(activity, "Izinkan Perizinan Kamera", RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    public void requestGalery(){
        if (hasGaleryPermission()){
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            activity.startActivityForResult(photoPickerIntent, REQUEST_GALERY);
        }else {
            EasyPermissions.requestPermissions(activity, "Izinkan Perizinan Storage", RC_GALERI_PERM, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private boolean hasGaleryPermission(){
        return EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA);
    }

    public Bitmap onActivityResult(int requestCode, int resultCode, Intent data) throws IOException {
        Bitmap bitmap = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            assert extras != null;
            bitmap = (Bitmap) extras.get("data");
        }else if (requestCode == REQUEST_GALERY && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedImage);
        }
        return bitmap;
    }
}
