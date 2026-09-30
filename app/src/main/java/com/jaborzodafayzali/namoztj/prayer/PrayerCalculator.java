package com.jaborzodafayzali.namoztj.prayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public final class PrayerCalculator {
    private PrayerCalculator() {}

    public static List<PrayerTime> calculate(double latitude,double longitude,
                                             Calendar date,TimeZone zone,int method,int school){
        Calendar c=(Calendar)date.clone();
        c.setTimeZone(zone==null?TimeZone.getDefault():zone);
        double jd=julian(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
        double d=jd-2451545.0+0.0008;
        double lw=-longitude;
        double n=Math.rint(d-lw/360.0);
        double jstar=2451545.0009+lw/360.0+n;
        double m=normalize(357.5291+0.98560028*(jstar-2451545.0));
        double cc=1.9148*Math.sin(rad(m))+0.0200*Math.sin(rad(2*m))+0.0003*Math.sin(rad(3*m));
        double lambda=normalize(m+102.9372+cc+180);
        double decl=Math.asin(Math.sin(rad(lambda))*Math.sin(rad(23.44)));
        double noon=720-4*longitude-equationOfTime(m);
        double fajrAngle=18.0;
        double ishaAngle=18.0;
        double sunrise=noon-hourAngle(latitude,decl,-0.833);
        double sunset=noon+hourAngle(latitude,decl,-0.833);
        double fajr=noon-hourAngle(latitude,decl,fajrAngle);
        double isha=noon+hourAngle(latitude,decl,ishaAngle);
        double shadowFactor=school==1?2.0:1.0;
        double asrAltitude=-Math.toDegrees(Math.atan(1.0/(shadowFactor+Math.tan(Math.abs(rad(latitude)-decl)))));
        double asr=noon+hourAngle(latitude,decl,asrAltitude);
        List<PrayerTime> out=new ArrayList<>();
        out.add(new PrayerTime("fajr","Fajr",format(fajr)));
        out.add(new PrayerTime("dhuhr","Dhuhr",format(noon)));
        out.add(new PrayerTime("asr","Asr",format(asr)));
        out.add(new PrayerTime("maghrib","Maghrib",format(sunset)));
        out.add(new PrayerTime("isha","Isha",format(isha)));
        return out;
    }

    private static double hourAngle(double lat,double decl,double angle){
        double x=(Math.sin(rad(angle))-Math.sin(rad(lat))*Math.sin(decl))/(Math.cos(rad(lat))*Math.cos(decl));
        if(x<=-1)return 720;
        if(x>=1)return 0;
        return Math.toDegrees(Math.acos(x))*4;
    }

    private static double equationOfTime(double m){
        double e=rad(m);
        return 4*Math.toDegrees(0.000075+0.001868*Math.cos(e)-0.032077*Math.sin(e)
                -0.014615*Math.cos(2*e)-0.040849*Math.sin(2*e));
    }

    private static double julian(int y,int m,int d){
        if(m<=2){y--;m+=12;}
        return Math.floor(365.25*(y+4716))+Math.floor(30.6001*(m+1))+d-1524.5;
    }

    private static double normalize(double x){x%=360;return x<0?x+360:x;}
    private static double rad(double x){return Math.toRadians(x);}
    private static String format(double minutes){
        minutes=((minutes%1440)+1440)%1440;
        int h=(int)(minutes/60),m=(int)Math.round(minutes%60);
        if(m==60){h=(h+1)%24;m=0;}
        return String.format(java.util.Locale.US,"%02d:%02d",h,m);
    }
}