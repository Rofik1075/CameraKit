package com.rofik.starterkit;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pub.devrel.easypermissions.EasyPermissions;

import static java.util.Objects.requireNonNull;

public class Rofik extends AppCompatActivity {
    public SharedPreferences sp;
    public Editor ed;
    public ProgressDialog pd;

    @SuppressLint("CommitPrefEdits")
    public Rofik() {
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        ed = sp.edit();
        pd = new ProgressDialog(this);
        pd.setMessage("Memuat data...");
        pd.setCancelable(false);
    }

    public void animasiRV(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.lytanimrv);

        recyclerView.setLayoutAnimation(controller);
        requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public String getSp(String key){
        return sp.getString(key, "");
    }

    public void requestPermision(String Pesan, int Kode, String... Permision){
        EasyPermissions.requestPermissions(this, Pesan, Kode, Permision);
    }

    public boolean hasPermision(String... Permision){
        return EasyPermissions.hasPermissions(this, Permision);
    }

    public String getPackageName(){
        return getApplicationContext().getPackageName();
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
        return tglformat.format(date);
    }
}
