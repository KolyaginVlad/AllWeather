package ru.kolyagin.allweather

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.kolyagin.allweather.di.ContextModule
import ru.kolyagin.allweather.di.DaggerDatabaseComponent
import ru.kolyagin.allweather.di.DatabaseModule
import ru.kolyagin.allweather.room.entity.Weather
import ru.kolyagin.allweather.utils.isNeedToUpdate

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
        val databaseComponent = DaggerDatabaseComponent.builder()
            .contextModule(ContextModule(this))
            .databaseModule(DatabaseModule())
            .build()
        val weatherDao = databaseComponent.getWeatherDao()
        if (isNeedToUpdate()) {
            Observable.just(
                Weather.getEmptyObjectWithName(OPEN_WEATHER_NAME),
            ).subscribeOn(Schedulers.io())
                .subscribe {
                    weatherDao.insert(it)
                }
        }
    }
}