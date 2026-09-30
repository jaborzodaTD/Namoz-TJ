package com.jaborzodafayzali.namoztj.prayer;

import android.content.Context;

public final class PrayerSettings {
    private static final String PREFS="namoz_prayer";
    private PrayerSettings(){}
    public static double latitude(Context c){return c.getSharedPreferences(PREFS,0).getFloat("lat",(float)38.5598);}
    public static double longitude(Context c){return c.getSharedPreferences(PREFS,0).getFloat("lon",(float)68.7870);}
    public static int method(Context c){return c.getSharedPreferences(PREFS,0).getInt("method",3);}
    public static int school(Context c){return c.getSharedPreferences(PREFS,0).getInt("school",1);}
    public static void saveLocation(Context c,double lat,double lon){c.getSharedPreferences(PREFS,0).edit().putFloat("lat",(float)lat).putFloat("lon",(float)lon).apply();}
    public static void saveCalculation(Context c,int method,int school){c.getSharedPreferences(PREFS,0).edit().putInt("method",method).putInt("school",school).apply();}
}