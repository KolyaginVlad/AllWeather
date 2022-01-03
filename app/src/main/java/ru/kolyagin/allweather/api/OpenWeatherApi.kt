package ru.kolyagin.allweather.api

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kolyagin.allweather.OPENWEATHER_API_KEY
import ru.kolyagin.allweather.api.models.open.OpenWeatherData

interface OpenWeatherApi {
    @GET("weather")
    fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appId: String = OPENWEATHER_API_KEY
    ): Observable<OpenWeatherData>
}