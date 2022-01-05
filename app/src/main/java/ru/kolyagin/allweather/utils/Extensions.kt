package ru.kolyagin.allweather.utils

import android.app.Application
import android.content.Context
import ru.kolyagin.allweather.*
import java.text.SimpleDateFormat
import java.util.*

fun Context.isNeedToUpdate(): Boolean =
    getSharedPreferences(SHARED_NAME, Application.MODE_PRIVATE).getLong(
        LAST_LOGIN_DATE,
        0
    ) + ONE_HOUR > Calendar.getInstance().time.time
            || getSharedPreferences(SHARED_NAME, Application.MODE_PRIVATE).getString(
        LAST_LANG,
        ""
    ) != Locale.getDefault().language

fun Context.updateLastLoginDateAndLang() =
    getSharedPreferences(SHARED_NAME, Application.MODE_PRIVATE).edit()
        .putLong(LAST_LOGIN_DATE, Calendar.getInstance().time.time)
        .putString(LAST_LANG, Locale.getDefault().language)
        .apply()

fun Context.savePage(index: Int) =
    getSharedPreferences(SHARED_NAME, Application.MODE_PRIVATE).edit()
        .putInt(LAST_PAGE, index)
        .apply()

fun Context.getPage(): Int =
    getSharedPreferences(SHARED_NAME, Application.MODE_PRIVATE).getInt(LAST_PAGE, 0)


fun Context.getDateStringWithTime(date: Date = Calendar.getInstance().time): String {
    return if (Locale.getDefault().language == "ru") {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        simpleDateFormat.format(date)
    } else {
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
        simpleDateFormat.format(date)
    }
}

fun Context.getDateString(date: Date = Calendar.getInstance().time): String {
    return if (Locale.getDefault().language == LANG_RU) {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        simpleDateFormat.format(date)
    } else {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        simpleDateFormat.format(date)
    }
}

fun Locale.getUnitsForOpenWeather(): String =
    when (language) {
        LANG_RU -> "metric"
        else -> "imperial"
    }

fun Locale.getLangForOpenWeather(): String =
    when (language) {
        LANG_RU -> OPENWEATHER_LANG_RU
        else -> OPENWEATHER_LANG_EN
    }