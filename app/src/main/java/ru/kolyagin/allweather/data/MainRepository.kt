package ru.kolyagin.allweather.data

import android.location.Location
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import ru.kolyagin.allweather.api.models.open.OpenWeatherData
import ru.kolyagin.allweather.room.entity.Weather

interface MainRepository {
    fun getWeatherFromDb(): Flowable<List<Weather>>
    fun getWeatherFromApi(location: Location): Observable<Weather>
}