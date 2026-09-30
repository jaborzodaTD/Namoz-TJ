package com.jaborzodafayzali.namoztj;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;
import com.jaborzodafayzali.namoztj.prayer.PrayerCalculator;
import com.jaborzodafayzali.namoztj.prayer.PrayerSettings;
import com.jaborzodafayzali.namoztj.prayer.PrayerTime;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFICATION_REQUEST=7001;
    private int dp(float v){return (int)(v*getResources().getDisplayMetrics().density+0.5f);}
    private TextView text(String s,float size,int color,boolean bold){
        TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);
        t.setTypeface(Typeface.DEFAULT,bold?Typeface.BOLD:Typeface.NORMAL);t.setGravity(Gravity.CENTER_VERTICAL);return t;
    }
    private MaterialCardView card(String title,String subtitle){
        MaterialCardView c=new MaterialCardView(this);c.setRadius(dp(22));c.setCardBackgroundColor(Color.rgb(16,27,43));
        c.setStrokeWidth(dp(1));c.setStrokeColor(Color.rgb(35,55,75));
        LinearLayout box=new LinearLayout(this);box.setOrientation(LinearLayout.VERTICAL);box.setPadding(dp(18),dp(14),dp(18),dp(14));
        box.addView(text(title,18,Color.WHITE,true),new LinearLayout.LayoutParams(-1,dp(30)));
        box.addView(text(subtitle,13,Color.rgb(170,183,199),false),new LinearLayout.LayoutParams(-1,dp(42)));
        c.addView(box);return c;
    }
    @Override protected void onCreate(Bundle state){
        LanguageManager.apply(this);super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(7,17,31));getWindow().setNavigationBarColor(Color.rgb(7,17,31));
        PrayerNotificationHelper.createChannel(this);requestNotifications();
        render();
    }
    @Override protected void onResume(){super.onResume(); if(android.os.Build.VERSION.SDK_INT>=31) ensureExactAlarmAccess(); render();}
    private void render(){
        LinearLayout content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);content.setPadding(dp(20),dp(18),dp(20),dp(20));content.setBackgroundColor(Color.rgb(7,17,31));
        content.addView(text(getString(R.string.app_name),28,Color.WHITE,true),new LinearLayout.LayoutParams(-1,dp(44)));
        content.addView(text(getString(R.string.tagline),14,Color.rgb(170,183,199),false),new LinearLayout.LayoutParams(-1,dp(34)));

        Calendar now=Calendar.getInstance(TimeZone.getDefault());
        List<PrayerTime> prayers=PrayerCalculator.calculate(PrayerSettings.latitude(this),PrayerSettings.longitude(this),now,TimeZone.getDefault(),PrayerSettings.method(this),PrayerSettings.school(this));
        String next=findNext(prayers);
        MaterialCardView hero=card(getString(R.string.today),"Next: "+next);
        hero.setCardBackgroundColor(Color.rgb(12,45,47));content.addView(hero,new LinearLayout.LayoutParams(-1,dp(92)));

        for(PrayerTime p:prayers){
            MaterialCardView c=card(label(p.id()),p.time());
            content.addView(c,new LinearLayout.LayoutParams(-1,dp(74)));
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)c.getLayoutParams();lp.bottomMargin=dp(8);c.setLayoutParams(lp);
        }
        add(content,"📖  "+getString(R.string.quran),"114 "+getString(R.string.quran)+" • offline architecture");
        add(content,"🤲  "+getString(R.string.duas),getString(R.string.duas)+" • "+getString(R.string.favorites));
        add(content,"🧭  "+getString(R.string.qibla),getString(R.string.qibla));
        add(content,"📿  "+getString(R.string.tasbih),getString(R.string.tasbih));
        add(content,"📅  "+getString(R.string.calendar),getString(R.string.calendar));
        add(content,"⚙  "+getString(R.string.settings),getString(R.string.language));
        TextView footer=text(String.format(getString(R.string.version),"1.0.0"),12,Color.rgb(120,140,160),false);footer.setGravity(Gravity.CENTER);content.addView(footer,new LinearLayout.LayoutParams(-1,dp(28)));
        ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);scroll.addView(content);setContentView(scroll);
        scheduleToday(prayers);
    }
    private void add(LinearLayout p,String a,String b){MaterialCardView c=card(a,b);p.addView(c,new LinearLayout.LayoutParams(-1,dp(78)));LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)c.getLayoutParams();lp.bottomMargin=dp(9);c.setLayoutParams(lp);}
    private String label(String id){
        if("fajr".equals(id))return getString(R.string.fajr);
        if("dhuhr".equals(id))return getString(R.string.dhuhr);
        if("asr".equals(id))return getString(R.string.asr);
        if("maghrib".equals(id))return getString(R.string.maghrib);
        return getString(R.string.isha);
    }
    private String findNext(List<PrayerTime> list){
        String now=new java.text.SimpleDateFormat("HH:mm",java.util.Locale.US).format(new java.util.Date());
        for(PrayerTime p:list)if(p.time().compareTo(now)>=0)return label(p.id())+" • "+p.time();
        return label(list.get(0).id())+" • "+list.get(0).time()+" (tomorrow)";
    }
    private void scheduleToday(List<PrayerTime> list){
        Calendar base=Calendar.getInstance();
        for(PrayerTime p:list){
            String[] x=p.time().split(":");Calendar at=(Calendar)base.clone();at.set(Calendar.HOUR_OF_DAY,Integer.parseInt(x[0]));at.set(Calendar.MINUTE,Integer.parseInt(x[1]));at.set(Calendar.SECOND,0);at.set(Calendar.MILLISECOND,0);
            PrayerAlarmScheduler.schedule(this,at.getTimeInMillis(),label(p.id()),p.time());
        }
    }
    private void ensureExactAlarmAccess(){
        AlarmManager am=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        if(am!=null&&!am.canScheduleExactAlarms()){}
    }
    private void requestNotifications(){
        if(Build.VERSION.SDK_INT>=33&&ContextCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.POST_NOTIFICATIONS},NOTIFICATION_REQUEST);
    }
}