package com.jaborzodafayzali.namoztj.prayer;

import android.content.Context;

public final class PrayerSettings {
    private static final String P="namoz_prayer";
    private PrayerSettings(){}
    public static double latitude(Context c){return c.getSharedPreferences(P,0).getFloat("lat",43.2389f);}
    public static double longitude(Context c){return c.getSharedPreferences(P,0).getFloat("lon",76.8897f);}
    public static int method(Context c){return c.getSharedPreferences(P,0).getInt("method",3);}
    public static int school(Context c){return c.getSharedPreferences(P,0).getInt("school",1);}
    public static void saveLocation(Context c,double lat,double lon){c.getSharedPreferences(P,0).edit().putFloat("lat",(float)lat).putFloat("lon",(float)lon).apply();}
    public static void saveCalculation(Context c,int method,int school){c.getSharedPreferences(P,0).edit().putInt("method",method).putInt("school",school).apply();}
}