package com.jaborzodafayzali.namoztj.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PrayerGuide {
    public record Prayer(String id, String tajik, String russian, String english, int rakah) {}
    public record GuideSection(String id, String tajik, String russian, String english) {}

    private PrayerGuide() {}

    public static List<Prayer> dailyPrayers() {
        return Collections.unmodifiableList(Arrays.asList(
            new Prayer("fajr", "Бомдод", "Фаджр", "Fajr", 2),
            new Prayer("dhuhr", "Пешин", "Зухр", "Dhuhr", 4),
            new Prayer("asr", "Аср", "Аср", "Asr", 4),
            new Prayer("maghrib", "Шом", "Магриб", "Maghrib", 3),
            new Prayer("isha", "Хуфтан", "Иша", "Isha", 4),
            new Prayer("tahajjud", "Таҳаҷҷуд", "Тахаджуд", "Tahajjud", 2)
        ));
    }

    public static List<GuideSection> sections() {
        return Collections.unmodifiableList(Arrays.asList(
            new GuideSection("wudu", "Тарзи вузӯ", "Как совершать вуду", "How to perform wudu"),
            new GuideSection("niyyah", "Ният", "Ният", "Intention"),
            new GuideSection("takbir", "Такбири таҳрима", "Такбир аль-ихрам", "Opening takbir"),
            new GuideSection("qiyam", "Қиём ва қироат", "Кыям и чтение", "Standing and recitation"),
            new GuideSection("ruku", "Рукуъ", "Рукуъ", "Ruku"),
            new GuideSection("sujud", "Саҷда", "Суджуд", "Sujud"),
            new GuideSection("tashahhud", "Ташаҳҳуд", "Ташаххуд", "Tashahhud"),
            new GuideSection("salam", "Салом", "Салам", "Salam"),
            new GuideSection("tahajjud", "Намози Таҳаҷҷуд", "Намаз Тахаджуд", "Tahajjud prayer"),
            new GuideSection("witr", "Намози Витр", "Намаз Витр", "Witr prayer")
        ));
    }
}