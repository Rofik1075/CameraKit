package com.rofik.starterkit;

import android.annotation.SuppressLint;
import android.app.Activity;
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

    Activity activity;
    Context context;

    @SuppressLint("CommitPrefEdits")
    public Rofik(Activity activity, Context context) {
        this.context = context;
        this.activity = activity;
        sp = activity.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        ed = sp.edit();
        pd = new ProgressDialog(this);
        pd.setMessage("Memuat data...");
        pd.setCancelable(false);
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
}
