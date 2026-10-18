package com.jaborzodafayzali.namoztj;
import android.content.BroadcastReceiver;import android.content.Context;import android.content.Intent;import java.util.Calendar;import java.util.List;import java.util.TimeZone;import com.jaborzodafayzali.namoztj.prayer.PrayerCalculator;import com.jaborzodafayzali.namoztj.prayer.PrayerSettings;import com.jaborzodafayzali.namoztj.prayer.PrayerTime;
public class PrayerAlarmReceiver extends BroadcastReceiver {
 @Override public void onReceive(Context context,Intent intent){
  PrayerNotificationHelper.createChannel(context);
  String prayer=intent.getStringExtra("prayer_name");String time=intent.getStringExtra("time");
  String canonical=prayer==null?"Namoz":prayer;
  boolean tg="tg".equals(LanguageManager.getLanguage(context));
  String localized=localize(canonical,tg);
  PrayerNotificationHelper.showPrayerNotification(context,localized,time==null?"":time);
  // Schedule tomorrow's five prayer alarms whenever one fires, keeping reminders recurring.
  Calendar tomorrow=Calendar.getInstance(TimeZone.getDefault());tomorrow.add(Calendar.DAY_OF_YEAR,1);
  List<PrayerTime> times=PrayerCalculator.calculate(PrayerSettings.latitude(context),PrayerSettings.longitude(context),tomorrow,TimeZone.getDefault(),PrayerSettings.method(context),PrayerSettings.school(context));
  for(PrayerTime p:times){try{String[] hm=p.time().split(":");Calendar at=(Calendar)tomorrow.clone();at.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hm[0]));at.set(Calendar.MINUTE,Integer.parseInt(hm[1]));at.set(Calendar.SECOND,0);at.set(Calendar.MILLISECOND,0);PrayerAlarmScheduler.schedule(context,at.getTimeInMillis(),p.name(),p.time());}catch(Exception ignored){}}
 }
 private String localize(String n,boolean tg){String s=n.toLowerCase(java.util.Locale.ROOT);switch(s){case "fajr":case "bomdod":return tg?"Бомдод":"Фаджр";case "dhuhr":case "zuhr":return tg?"Пешин":"Зухр";case "asr":return "Аср";case "maghrib":return tg?"Шом":"Магриб";case "isha":return tg?"Хуфтан":"Иша";default:return n;}}
}