package com.jaborzodafayzali.namoztj.prayer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.jaborzodafayzali.namoztj.PrayerAlarmReceiver;

public final class PrayerAlarmScheduler {
    private PrayerAlarmScheduler(){}
    public static boolean schedule(Context c,long trigger,String prayer,String time){
        if(trigger<=System.currentTimeMillis()) return false;
        AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);
        if(am==null)return false;
        Intent i=new Intent(c,PrayerAlarmReceiver.class).putExtra("prayer_name",prayer).putExtra("time",time);
        int code=Math.abs((prayer+"_"+time).hashCode());
        PendingIntent pi=PendingIntent.getBroadcast(c,code,i,PendingIntent.FLAG_UPDATE_CURRENT|(Build.VERSION.SDK_INT>=23?PendingIntent.FLAG_IMMUTABLE:0));
        if(Build.VERSION.SDK_INT>=31 && !am.canScheduleExactAlarms()){
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,trigger,pi); return true;
        }
        if(Build.VERSION.SDK_INT>=23) am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,trigger,pi);
        else am.setExact(AlarmManager.RTC_WAKEUP,trigger,pi);
        return true;
    }
}