package ru.kolyagin.allweather

import android.app.Application
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