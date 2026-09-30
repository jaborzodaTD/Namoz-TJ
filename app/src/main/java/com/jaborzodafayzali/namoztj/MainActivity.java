package com.jaborzodafayzali.namoztj;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFICATION_REQUEST = 7001;
    private int dp(float v){ return (int)(v*getResources().getDisplayMetrics().density+0.5f); }

    private TextView text(String s,float size,int color,boolean bold){
        TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        t.setTypeface(Typeface.DEFAULT,bold?Typeface.BOLD:Typeface.NORMAL); t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }

    private MaterialCardView card(String title,String subtitle){
        MaterialCardView c=new MaterialCardView(this);
        c.setRadius(dp(22)); c.setCardBackgroundColor(Color.rgb(16,27,43));
        c.setStrokeWidth(dp(1)); c.setStrokeColor(Color.rgb(35,55,75));
        LinearLayout box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(18),dp(14),dp(18),dp(14));
        box.addView(text(title,18,Color.WHITE,true),new LinearLayout.LayoutParams(-1,dp(30)));
        box.addView(text(subtitle,13,Color.rgb(170,183,199),false),new LinearLayout.LayoutParams(-1,dp(42)));
        c.addView(box); return c;
    }

    @Override protected void onCreate(Bundle state){
        LanguageManager.apply(this);
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(7,17,31));
        getWindow().setNavigationBarColor(Color.rgb(7,17,31));
        PrayerNotificationHelper.createChannel(this);
        requestNotifications();

        LinearLayout content=new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20),dp(18),dp(20),dp(20));
        content.setBackgroundColor(Color.rgb(7,17,31));

        TextView brand=text(getString(R.string.app_name),28,Color.WHITE,true);
        content.addView(brand,new LinearLayout.LayoutParams(-1,dp(44)));
        TextView sub=text(getString(R.string.tagline),14,Color.rgb(170,183,199),false);
        content.addView(sub,new LinearLayout.LayoutParams(-1,dp(34)));

        MaterialCardView hero=card(getString(R.string.today),getString(R.string.choose_city));
        hero.setCardBackgroundColor(Color.rgb(12,45,47));
        content.addView(hero,new LinearLayout.LayoutParams(-1,dp(108)));

        LinearLayout grid=new LinearLayout(this);
        grid.setOrientation(LinearLayout.VERTICAL);
        grid.setPadding(0,dp(14),0,0);

        addFeature(grid,"🕌  "+getString(R.string.prayer_times),getString(R.string.fajr)+" • "+getString(R.string.dhuhr)+" • "+getString(R.string.asr)+" • "+getString(R.string.maghrib)+" • "+getString(R.string.isha));
        addFeature(grid,"📖  "+getString(R.string.quran),"114 "+getString(R.string.quran)+" • offline");
        addFeature(grid,"🤲  "+getString(R.string.duas),getString(R.string.duas)+" • "+getString(R.string.favorites));
        addFeature(grid,"🧭  "+getString(R.string.qibla),getString(R.string.qibla));
        addFeature(grid,"📿  "+getString(R.string.tasbih),getString(R.string.tasbih));
        addFeature(grid,"📅  "+getString(R.string.calendar),getString(R.string.calendar));
        addFeature(grid,"⚙  "+getString(R.string.settings),getString(R.string.language));

        content.addView(grid,new LinearLayout.LayoutParams(-1,0,1));
        TextView footer=text(getString(R.string.version),"12".equals("12")?12:12,Color.rgb(120,140,160),false);
        footer.setText(String.format(getString(R.string.version),"1.0.0"));
        footer.setGravity(Gravity.CENTER);
        content.addView(footer,new LinearLayout.LayoutParams(-1,dp(28)));

        ScrollView scroll=new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.addView(content);
        setContentView(scroll);
    }

    private void addFeature(LinearLayout parent,String title,String subtitle){
        MaterialCardView c=card(title,subtitle);
        parent.addView(c,new LinearLayout.LayoutParams(-1,dp(82)));
        LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)c.getLayoutParams();
        lp.bottomMargin=dp(9);
        c.setLayoutParams(lp);
    }

    private void requestNotifications(){
        if(android.os.Build.VERSION.SDK_INT>=33 && ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.POST_NOTIFICATIONS},NOTIFICATION_REQUEST);
    }
}
