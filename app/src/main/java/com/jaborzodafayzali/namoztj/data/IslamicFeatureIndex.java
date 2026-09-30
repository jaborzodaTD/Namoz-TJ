package com.jaborzodafayzali.namoztj.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class IslamicFeatureIndex {
    public record Feature(String id, String titleTajik, String titleRussian, String emoji) {}
    private IslamicFeatureIndex() {}

    public static List<Feature> all() {
        return Collections.unmodifiableList(Arrays.asList(
            new Feature("prayer_times","Вақти намоз","Время намаза","🕌"),
            new Feature("quran","Қуръон","Коран","📖"),
            new Feature("duas","Дуоҳо","Дуа","🤲"),
            new Feature("prayer_guide","Тарзи намоз","Как читать намаз","🧎"),
            new Feature("qibla","Қибла","Кыбла","🧭"),
            new Feature("tasbih","Тасбеҳ","Тасбих","📿"),
            new Feature("calendar","Тақвими исломӣ","Исламский календарь","🌙"),
            new Feature("favorites","Избранное","Избранное","⭐"),
            new Feature("search","Ҷустуҷӯ","Поиск","🔎"),
            new Feature("settings","Танзимот","Настройки","⚙️")
        ));
    }
}