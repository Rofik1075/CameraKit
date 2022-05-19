## Require
Pasang di build.gradle dan dependency nya:
```
1. maven { url 'https://jitpack.io' }
2. implementation 'com.github.Rofik1075:ImagePicker:3.0.0'
```

## Cara Menggunakan
```
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  ImagePicker.cameraOnly(this); // Buka Kamera
  ImagePicker.galeryOnly(this); // Buka Galeri
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  Bitmap bitmap;
    try {
      bitmap = ImagePicker.onActivityResult(this, requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.foto);
          imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
          e.printStackTrace();
        }
   }
}
```

  
