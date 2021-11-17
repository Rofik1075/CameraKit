package com.rofik.camerakit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = new Camera(this, this);

        Button button = findViewById(R.id.btn_coba);
        button.setOnClickListener(view -> camera.requestCamera());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = camera.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.foto);
        imageView.setImageBitmap(bitmap);
    }
}