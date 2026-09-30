package com.jaborzodafayzali.namoztj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import com.jaborzodafayzali.namoztj.prayer.PrayerCalculator;
import com.jaborzodafayzali.namoztj.prayer.PrayerSettings;
import com.jaborzodafayzali.namoztj.prayer.PrayerTime;

public class PrayerBootReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context c,Intent intent){
        PrayerNotificationHelper.createChannel(c);
        String a=intent.getAction();
        if(Intent.ACTION_BOOT_COMPLETED.equals(a)||Intent.ACTION_MY_PACKAGE_REPLACED.equals(a)||"android.app.action.SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED".equals(a)){
            Calendar now=Calendar.getInstance(TimeZone.getDefault());
            List<PrayerTime> times=PrayerCalculator.calculate(PrayerSettings.latitude(c),PrayerSettings.longitude(c),now,TimeZone.getDefault(),PrayerSettings.method(c),PrayerSettings.school(c));
            for(PrayerTime p:times){
                String[] x=p.time().split(":"); Calendar at=(Calendar)now.clone();
                at.set(Calendar.HOUR_OF_DAY,Integer.parseInt(x[0]));at.set(Calendar.MINUTE,Integer.parseInt(x[1]));at.set(Calendar.SECOND,0);at.set(Calendar.MILLISECOND,0);
                if(at.getTimeInMillis()>System.currentTimeMillis()) PrayerAlarmScheduler.schedule(c,at.getTimeInMillis(),p.name(),p.time());
            }
        }
    }
}