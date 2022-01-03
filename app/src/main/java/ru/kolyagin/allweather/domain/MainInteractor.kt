package ru.kolyagin.allweather.domain

import android.location.Location

interface MainInteractor {
    fun setupInteractorOut(out: MainInteractorOut)
    fun fetchWeather(location: Location)
    fun getWeatherFromDb()
}