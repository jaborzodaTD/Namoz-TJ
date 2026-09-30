package com.jaborzodafayzali.namoztj.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class IslamicContent {
    public record Category(String id,String tajik,String russian,String english){}
    public record SurahMeta(int number,String arabicName,String tajikName,String russianName,int verses){}
    public record DuaMeta(String id,String tajikTitle,String russianTitle,String category){}
    private IslamicContent(){}

    public static List<Category> duaCategories(){return Collections.unmodifiableList(List.of(
        new Category("morning","Дуоҳои саҳар","Утренние дуа","Morning"),
        new Category("evening","Дуоҳои шом","Вечерние дуа","Evening"),
        new Category("prayer","Дуоҳои намоз","Дуа намаза","Prayer"),
        new Category("after_fajr","Пас аз Бомдод","После Фаджра","After Fajr"),
        new Category("after_meal","Пас аз таом","После еды","After meal"),
        new Category("before_sleep","Пеш аз хоб","Перед сном","Before sleep"),
        new Category("tahajjud","Дуои Таҳаҷҷуд","Дуа Тахаджуда","Tahajjud"),
        new Category("travel","Сафар","Путешествие","Travel"),
        new Category("protection","Ҳифз ва паноҳ","Защита","Protection"),
        new Category("forgiveness","Тавба ва истиғфор","Покаяние и прощение","Forgiveness"),
        new Category("family","Оила","Семья","Family"),
        new Category("rizq","Ризқ ва баракат","Ризк и барака","Provision"),
        new Category("quranic","Дуоҳои Қуръонӣ","Коранические дуа","Quranic")
    ));}

    public static List<DuaMeta> coreDuaIndex(){return Collections.unmodifiableList(List.of(
        new DuaMeta("dua_after_fajr","Дуо пас аз Бомдод","Дуа после Фаджра","after_fajr"),
        new DuaMeta("dua_after_meal","Дуо пас аз таом","Дуа после еды","after_meal"),
        new DuaMeta("dua_before_sleep","Дуо пеш аз хоб","Дуа перед сном","before_sleep"),
        new DuaMeta("dua_tahajjud","Дуои Таҳаҷҷуд","Дуа Тахаджуда","tahajjud"),
        new DuaMeta("dua_travel","Дуои сафар","Дуа путешествия","travel"),
        new DuaMeta("dua_forgiveness","Дуои истиғфор","Дуа об истигфаре","forgiveness"),
        new DuaMeta("dua_rizq","Дуо барои ризқ","Дуа о пропитании","rizq"),
        new DuaMeta("dua_family","Дуо барои оила","Дуа за семью","family")
    ));}

    // Names/counts are structural placeholders only. Arabic Quran and translations
    // must be imported from a verified/licensed dataset before release.
    public static List<SurahMeta> surahIndex(){
        List<SurahMeta> list=new ArrayList<>();
        for(int i=1;i<=114;i++) list.add(new SurahMeta(i,"","Сура "+i,"Сура "+i,0));
        return Collections.unmodifiableList(list);
    }
}