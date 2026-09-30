package com.jaborzodafayzali.namoztj;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import java.util.Locale;

public final class LanguageManager {
    private static final String PREFS = "namoz_settings";
    private static final String KEY_LANGUAGE = "language";
    public static final String TAJIK = "tg";
    public static final String RUSSIAN = "ru";
    public static final String ENGLISH = "en";
    private LanguageManager() {}

    public static String getLanguage(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_LANGUAGE, RUSSIAN);
    }

    public static void setLanguage(Context context, String language) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putString(KEY_LANGUAGE, language).apply();
        apply(context, language);
    }

    public static void apply(Context context) {
        apply(context, getLanguage(context));
    }

    private static void apply(Context context, String language) {
        Locale locale = Locale.forLanguageTag(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocales(new android.os.LocaleList(locale));
        } else {
            configuration.locale = locale;
        }
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
