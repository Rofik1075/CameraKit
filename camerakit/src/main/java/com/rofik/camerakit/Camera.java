package com.rofik.camerakit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class Camera {
    Context context;
    Activity activity;
    private static final int RC_CAMERA_PERM = 123;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

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

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA);
    }

    public Bitmap onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            assert extras != null;
            bitmap = (Bitmap) extras.get("data");
        }
        return bitmap;
    }
}
