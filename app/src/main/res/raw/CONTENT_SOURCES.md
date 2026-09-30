# Content source policy

QURAN
Use a verified Qur'an dataset and preserve its source/translation attribution. Quran Foundation's current API documents chapters, verses, translations, tafsir, audio and recitations, but its Content API requires credentials and says mobile apps must not embed a client secret. For an offline release, the build pipeline should import an explicitly licensed/static dataset instead of placing API credentials in the APK.

PRAYER TIMES
AlAdhan provides prayer-time endpoints and exposes calculation methods plus Shafi/Hanafi school settings. The app should keep calculation method, school, timezone and optional tuning configurable because times can differ between authorities.

DUAS/HADITH
Only verified sources with clear attribution/licensing should be imported. Do not synthesize Arabic religious text.

AUDIO / AZAN
Use an explicitly licensed/public-domain or user-supplied recording with documented rights before bundling it in the release APK.
