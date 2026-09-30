package com.jaborzodafayzali.namoztj;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PrayerAlarmReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        PrayerNotificationHelper.createChannel(context);
        String prayer = intent.getStringExtra("prayer_name");
        String time = intent.getStringExtra("time");
        PrayerNotificationHelper.showPrayerNotification(
                context,
                prayer == null ? "Намоз" : prayer,
                time == null ? "" : time
        );
    }
}