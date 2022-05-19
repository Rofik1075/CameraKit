package com.rofik.camerakit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.IOException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Images.Media.getBitmap;
import static java.util.Objects.requireNonNull;
import static pub.devrel.easypermissions.EasyPermissions.hasPermissions;
import static pub.devrel.easypermissions.EasyPermissions.requestPermissions;

public class ImagePicker {
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_GALERI_PERM = 124;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALERY = 2;

    public static void cameraOnly(Activity activity) {
        if (hasCameraPermission(activity)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            requestPermissions(activity, "Izinkan Perizinan Kamera", RC_CAMERA_PERM, CAMERA);
        }
    }

    public static void galeryOnly(Activity activity) {
        if (hasGaleryPermission(activity)) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            activity.startActivityForResult(photoPickerIntent, REQUEST_GALERY);
        } else {
            requestPermissions(activity, "Izinkan Perizinan Storage", RC_GALERI_PERM, READ_EXTERNAL_STORAGE);
        }
    }

    private static boolean hasGaleryPermission(Context context) {
        return hasPermissions(context, READ_EXTERNAL_STORAGE);
    }

    private static boolean hasCameraPermission(Context context) {
        return hasPermissions(context, CAMERA);
    }

    public static Bitmap onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) throws IOException {
        Bitmap bitmap = null;
        if (data != null){
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                assert extras != null;
                bitmap = (Bitmap) requireNonNull(extras.get("data"));
            } else if (requestCode == REQUEST_GALERY && resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                bitmap = resizeBitmap(getBitmap(activity.getContentResolver(), selectedImage));
            }
        }
        return bitmap;
    }

    private static Bitmap resizeBitmap(Bitmap bitmap) {
        int MAX_ALLOWED_RESOLUTION = 1024;
        int outWidth;
        int outHeight;
        int inWidth = bitmap.getWidth();
        int inHeight = bitmap.getHeight();
        if(inWidth > inHeight){
            outWidth = MAX_ALLOWED_RESOLUTION;
            outHeight = (inHeight * MAX_ALLOWED_RESOLUTION) / inWidth;
        } else {
            outHeight = MAX_ALLOWED_RESOLUTION;
            outWidth = (inWidth * MAX_ALLOWED_RESOLUTION) / inHeight;
        }

        return Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
    }
}
