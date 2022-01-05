package ru.kolyagin.allweather.data

import android.location.Location
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.kolyagin.allweather.OPEN_WEATHER_NAME
import ru.kolyagin.allweather.api.OpenWeatherApi
import ru.kolyagin.allweather.di.DaggerApiComponent
import ru.kolyagin.allweather.room.dao.WeatherDao
import ru.kolyagin.allweather.room.entity.Weather
import ru.kolyagin.allweather.utils.getLangForOpenWeather
import ru.kolyagin.allweather.utils.getUnitsForOpenWeather
import java.util.*
import javax.inject.Inject

class MainRepositoryImpl(private val weatherDao: WeatherDao) : MainRepository {

    init {
        DaggerApiComponent.create().inject(this)
    }

    @Inject
    lateinit var openWeatherApi: OpenWeatherApi

    override fun getWeatherFromDb(): Flowable<List<Weather>> =
        weatherDao.getAll().observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())

    override fun getWeatherFromApi(location: Location): Observable<Weather> =
        Observable.merge(getOpenWeatherObservable(location), Observable.empty())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext { weatherDao.insert(it) }


    private fun getOpenWeatherObservable(location: Location): Observable<Weather> =
        openWeatherApi.getWeather(
            lat = location.latitude.toString(),
            lon = location.longitude.toString(),
            units = Locale.getDefault().getUnitsForOpenWeather(),
            lang = Locale.getDefault().getLangForOpenWeather()
        ).flatMap { openWeatherData ->
            Observable.just(
                Weather(
                    OPEN_WEATHER_NAME,
                    openWeatherData.main.temp,
                    openWeatherData.main.humidity.toDouble(),
                    openWeatherData.wind.speed,
                    openWeatherData.weather[0].description.replaceFirstChar { it.uppercase() },
                    openWeatherData.name,
                    "https://openweathermap.org/img/wn/${openWeatherData.weather[0].icon}@2x.png",
                    true
                )
            )
        }
}