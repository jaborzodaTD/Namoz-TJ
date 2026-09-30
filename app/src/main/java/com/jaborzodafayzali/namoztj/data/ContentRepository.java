package com.jaborzodafayzali.namoztj.data;

import java.util.List;

public final class ContentRepository {
    private ContentRepository() {}

    public static List<IslamicContent.Category> duaCategories() {
        return IslamicContent.duaCategories();
    }

    public static List<IslamicContent.DuaMeta> coreDuas() {
        return IslamicContent.coreDuaIndex();
    }

    public static List<IslamicContent.SurahMeta> surahs() {
        return IslamicContent.surahIndex();
    }
}