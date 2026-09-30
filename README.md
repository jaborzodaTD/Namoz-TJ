# Namoz TJ 🕌

A production-oriented Islamic Android application focused on accurate, offline-first content and reliable prayer reminders.

## Planned content library
- Qur'an: all 114 surahs, Arabic text, Tajik and Russian translations.
- Duas: morning/evening, prayer, after Fajr, after meals, before sleep, Tahajjud, travel, protection, forgiveness, family, rizq/baraka and Qur'anic supplications.
- Prayer times: location/manual city, calculation-method and madhhab settings.
- Azan: notification channel + scheduled alarm foundation, with user-selectable sound mode.
- Qibla, Tasbih, Islamic calendar, bookmarks, search and reading preferences.

## Content integrity
Qur'an and hadith/dua text is not generated from memory. Production content will be imported from verified/licensed sources, preserving Arabic text and attribution. Metadata is separated from content so translations and future languages can be updated without rewriting the app.

## Reliability
Core content is designed to work offline. Prayer alarms use Android alarm APIs with an inexact fallback where exact alarms are unavailable. Notification permission is requested only when needed.

## Build
GitHub Actions builds the debug APK with Java 17.
