package com.rofik.starterkit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fungsi.starterkit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

import static java.util.Objects.requireNonNull;

public class Fungsi extends AppCompatActivity {
    public SharedPreferences sp;
    public Editor ed;
    public ProgressDialog pd;
    public FirebaseAuth fa;
    public DatabaseReference dr;
    public StorageReference sr;

    Activity activity;
    Context context;

    @SuppressLint("CommitPrefEdits")
    public Fungsi(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
        sp = activity.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        ed = sp.edit();

        dr = FirebaseDatabase.getInstance().getReference();
        sr = FirebaseStorage.getInstance().getReference();
        fa = FirebaseAuth.getInstance();

        pd = new ProgressDialog(context);
        pd.setMessage("Memuat data...");
        pd.setCancelable(false);
    }

    public interface getDatasnapshot{
        void onDatasnapshot(DataSnapshot datasnapshot);
    }

    public void getSingleDR(Query drx, getDatasnapshot ds){
        drx.keepSynced(true);
        drx.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ds.onDatasnapshot(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        drx.keepSynced(false);
    }

    public void getValueDR(Query drx, getDatasnapshot ds){
        drx.keepSynced(true);
        drx.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ds.onDatasnapshot(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        drx.keepSynced(false);
    }

    public void animasiRV(final RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.lytanimrv);

        recyclerView.setLayoutAnimation(controller);
        requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public String getSp(String key){
        return sp.getString(key, "");
    }

    public void requestPermision(String Pesan, int Kode, String... Permision){
        EasyPermissions.requestPermissions(activity, Pesan, Kode, Permision);
    }

    public boolean hasPermision(String... Permision){
        return EasyPermissions.hasPermissions(context, Permision);
    }

    public String getPackageName(){
        return context.getApplicationContext().getPackageName();
    }

    @SuppressLint("SimpleDateFormat")
    public String jamSekarang(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(cal.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public String tglSekarang(String kode){
        DateFormat tglformat = null;
        if(kode.equals("indo")){
            tglformat = new SimpleDateFormat("dd-MM-yyyy");
        }else if(kode.equals("eropa")){
            tglformat = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date date = new Date();
        return requireNonNull(tglformat).format(date);
    }

    public double jarakGratis(String k1, String k2, String l1, String l2) {
        Location loc1 = new Location("");
        loc1.setLatitude(Float.parseFloat(l1));
        loc1.setLongitude(Float.parseFloat(l2));

        Location loc2 = new Location("");
        loc2.setLatitude(Float.parseFloat(k1));
        loc2.setLongitude(Float.parseFloat(k2));

        return loc1.distanceTo(loc2) / 1000;
    }


    public String Rupiah(int uang) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat df = NumberFormat.getCurrencyInstance(localeID);
        CharSequence cs = df.format(uang);
        String hasil = cs.toString();
        return hasil.substring(2).replaceAll(",00", "");
    }

    public Bitmap base64StrtoBitmap(String base64Str) throws IllegalArgumentException {
        Bitmap bitmap = null;
        try {
            byte[] decodedBytes = Base64.decode(
                    base64Str.substring(base64Str.indexOf(",") + 1),
                    Base64.DEFAULT
            );

            bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public String bitmaptoBase64Str(Bitmap bitmap) {
        String hasil = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            hasil = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
        return hasil;
    }

    public void panggilMap(String lat, String lon) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lon);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        String goolgeMap = "com.google.android.apps.maps";
        mapIntent.setPackage(goolgeMap);
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "Google Maps Belum Terinstal. Silahkan Install Terlebih dahulu.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void gantiStatusbar(int warna) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(warna));
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public void setTransparantStatusBar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }

        if (Build.VERSION.SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setToast(String Pesan){
        Toast.makeText(context, Pesan, Toast.LENGTH_SHORT).show();
    }
}
