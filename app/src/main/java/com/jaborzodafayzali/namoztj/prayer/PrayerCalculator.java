package com.jaborzodafayzali.namoztj.prayer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public final class PrayerCalculator {
    private PrayerCalculator() {}

    public static List<PrayerTime> calculate(double latitude, double longitude, LocalDate date,
                                             ZoneId zone, int method, int school) {
        double jd = julian(date);
        double d = jd - 2451545.0 + 0.0008;
        double lw = -longitude;
        double n = Math.rint(d - lw / 360.0);
        double jstar = 2451545.0009 + lw / 360.0 + n;
        double m = normalize(357.5291 + 0.98560028 * (jstar - 2451545.0));
        double c = 1.9148 * Math.sin(rad(m)) + 0.0200 * Math.sin(rad(2*m)) + 0.0003 * Math.sin(rad(3*m));
        double lambda = normalize(m + 102.9372 + c + 180);
        double decl = Math.asin(Math.sin(rad(lambda)) * Math.sin(rad(23.44)));
        double noon = 720 - 4 * longitude - equationOfTime(lambda, m);

        double fajrAngle = method == 1 ? 18.0 : 18.0;
        double ishaAngle = method == 1 ? 18.0 : 18.0;
        double sunrise = noon - hourAngle(latitude, decl, -0.833);
        double sunset = noon + hourAngle(latitude, decl, -0.833);
        double fajr = noon - hourAngle(latitude, decl, fajrAngle);
        double isha = noon + hourAngle(latitude, decl, ishaAngle);

        double asrFactor = school == 1 ? 2.0 : 1.0;
        double asr = noon + hourAngle(latitude, decl, -Math.toDegrees(Math.atan(1.0 / (asrFactor + Math.tan(Math.abs(rad(latitude-deg(decl))))))));
        double dhuhr = noon;

        ZoneId z = zone == null ? ZoneId.systemDefault() : zone;
        List<PrayerTime> out = new ArrayList<>();
        out.add(new PrayerTime("fajr","Fajr",format(fajr)));
        out.add(new PrayerTime("dhuhr","Dhuhr",format(dhuhr)));
        out.add(new PrayerTime("asr","Asr",format(asr)));
        out.add(new PrayerTime("maghrib","Maghrib",format(sunset)));
        out.add(new PrayerTime("isha","Isha",format(isha)));
        return out;
    }

    private static double hourAngle(double lat, double decl, double angle) {
        double numerator = Math.sin(rad(angle)) - Math.sin(rad(lat))*Math.sin(decl);
        double denominator = Math.cos(rad(lat))*Math.cos(decl);
        double x = numerator / denominator;
        if (x <= -1) return 12 * 60;
        if (x >= 1) return 0;
        return Math.toDegrees(Math.acos(x)) * 4;
    }

    private static double equationOfTime(double lambda, double m) {
        double eps = rad(23.44);
        double L = rad(lambda);
        double eq = 4 * Math.toDegrees(
            0.000075 + 0.001868*Math.cos(rad(m)) - 0.032077*Math.sin(rad(m))
            - 0.014615*Math.cos(2*L) - 0.040849*Math.sin(2*L));
        return eq;
    }

    private static double julian(LocalDate date) {
        int y=date.getYear(), m=date.getMonthValue();
        int d=date.getDayOfMonth();
        if(m<=2){y--;m+=12;}
        return Math.floor(365.25*(y+4716))+Math.floor(30.6001*(m+1))+d-1524.5;
    }
    private static double normalize(double x){x%=360; return x<0?x+360:x;}
    private static double rad(double x){return Math.toRadians(x);}
    private static double deg(double r){return Math.toDegrees(r);}
    private static String format(double minutes){
        minutes=((minutes%1440)+1440)%1440;
        int h=(int)(minutes/60), m=(int)Math.round(minutes%60);
        if(m==60){h=(h+1)%24;m=0;}
        return String.format(java.util.Locale.US,"%02d:%02d",h,m);
    }
}