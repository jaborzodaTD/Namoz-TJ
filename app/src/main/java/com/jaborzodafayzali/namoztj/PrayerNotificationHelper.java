package com.jaborzodafayzali.namoztj;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public final class PrayerNotificationHelper {
    // New channel ID is intentional: Android does not replace sound on an existing channel.
    public static final String CHANNEL_ID="azan_prayer_v2";
    private PrayerNotificationHelper(){}
    public static void createChannel(Context context){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,
                    context.getString(R.string.notification_channel_azan),NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(context.getString(R.string.notification_channel_azan_desc));
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0,350,200,350});
            Uri sound=Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.azan);
            AudioAttributes audio=new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            channel.setSound(sound,audio);
            NotificationManager nm=context.getSystemService(NotificationManager.class);
            if(nm!=null)nm.createNotificationChannel(channel);
        }
    }
    public static void showPrayerNotification(Context context,String prayerName,String time){
        if(Build.VERSION.SDK_INT>=33&&context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED)return;
        Intent intent=new Intent(context,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(context,1001,intent,
                PendingIntent.FLAG_UPDATE_CURRENT|(Build.VERSION.SDK_INT>=23?PendingIntent.FLAG_IMMUTABLE:0));
        NotificationCompat.Builder b=new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("🕌 "+prayerName)
                .setContentText(("tg".equals(LanguageManager.getLanguage(context))?"Вақти намоз — ":"Время намаза — ")+time)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true).setContentIntent(pi)
                .setVibrate(new long[]{0,350,200,350});
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.O){Uri sound=Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.azan);b.setSound(sound,new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());}
        NotificationManagerCompat.from(context).notify(Math.abs((prayerName+time).hashCode()),b.build());
    }
}