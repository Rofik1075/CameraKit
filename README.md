## Require
Pasang di build.gradle dan dependency nya:
```
1. maven { url 'https://jitpack.io' }
2. implementation 'com.github.Rofik1075:CameraKit:1.0.0'
```

## Cara Menggunakan
```
private Camera camera;

@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  camera = new Camera(this, this); //inisialisasi

  camera.requestCamera(); //open kamera
}    

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  Bitmap bitmap = camera.onActivityResult(requestCode, resultCode, data);
  ImageView imageView = findViewById(R.id.foto);
  imageView.setImageBitmap(bitmap);
}
```

  
