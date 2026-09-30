package com.jaborzodafayzali.namoztj.prayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public final class PrayerCalculator {
    private PrayerCalculator(){}
    public static final int METHOD_MWL=3;
    public static final int METHOD_ISNA=2;
    public static final int METHOD_EGYPT=5;
    public static final int METHOD_MAKKAH=4;
    public static final int SCHOOL_SHAFI=0;
    public static final int SCHOOL_HANAFI=1;

    public static List<PrayerTime> calculate(double lat,double lon,Calendar date,TimeZone zone,int method,int school){
        Calendar c=(Calendar)date.clone(); c.setTimeZone(zone==null?TimeZone.getDefault():zone);
        double jd=julian(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH));
        double d=jd-2451545.0+0.0008, lw=-lon, n=Math.rint(d-lw/360.0);
        double j=2451545.0009+lw/360.0+n;
        double m=norm(357.5291+0.98560028*(j-2451545.0));
        double cc=1.9148*Math.sin(r(m))+0.0200*Math.sin(2*r(m))+0.0003*Math.sin(3*r(m));
        double lambda=norm(m+102.9372+cc+180);
        double decl=Math.asin(Math.sin(r(lambda))*Math.sin(r(23.44)));
        double noon=720-4*lon-eot(m);
        double[] angles=angles(method);
        double fajr=noon-ha(lat,decl,angles[0]), sunrise=noon-ha(lat,decl,-0.833);
        double sunset=noon+ha(lat,decl,-0.833), isha=noon+ha(lat,decl,angles[1]);
        double factor=school==SCHOOL_HANAFI?2.0:1.0;
        double asrAngle=Math.toDegrees(Math.atan(1.0/(factor+Math.tan(Math.abs(r(lat)-decl)))));\n        double asr=noon+ha(lat,decl,-asrAngle);
        List<PrayerTime> out=new ArrayList<>();
        out.add(new PrayerTime("fajr","Fajr",fmt(fajr))); out.add(new PrayerTime("dhuhr","Dhuhr",fmt(noon)));
        out.add(new PrayerTime("asr","Asr",fmt(asr))); out.add(new PrayerTime("maghrib","Maghrib",fmt(sunset))); out.add(new PrayerTime("isha","Isha",fmt(isha)));
        return out;
    }
    private static double[] angles(int method){
        switch(method){case METHOD_ISNA:return new double[]{15,15};case METHOD_EGYPT:return new double[]{19.5,17.5};case METHOD_MAKKAH:return new double[]{18.5,90};default:return new double[]{18,17};}
    }
    private static double ha(double lat,double decl,double angle){
        double x=(Math.sin(r(angle))-Math.sin(r(lat))*Math.sin(decl))/(Math.cos(r(lat))*Math.cos(decl));
        if(x<=-1)return 720;if(x>=1)return 0;return Math.toDegrees(Math.acos(x))*4;
    }
    private static double eot(double m){
        double e=r(m);return 4*Math.toDegrees(0.000075+0.001868*Math.cos(e)-0.032077*Math.sin(e)-0.014615*Math.cos(2*e)-0.040849*Math.sin(2*e));
    }
    private static double julian(int y,int m,int d){if(m<=2){y--;m+=12;}return Math.floor(365.25*(y+4716))+Math.floor(30.6001*(m+1))+d-1524.5;}
    private static double norm(double x){x%=360;return x<0?x+360:x;}
    private static double r(double x){return Math.toRadians(x);}
    private static String fmt(double min){min=((min%1440)+1440)%1440;int h=(int)(min/60),m=(int)Math.round(min%60);if(m==60){h=(h+1)%24;m=0;}return String.format(java.util.Locale.US,"%02d:%02d",h,m);}
}