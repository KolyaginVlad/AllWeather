package ru.kolyagin.allweather.domain

import android.location.Location
import ru.kolyagin.allweather.data.MainRepository

class MainInteractorImpl(private val repository: MainRepository) : MainInteractor {
    private lateinit var out: MainInteractorOut

    override fun setupInteractorOut(out: MainInteractorOut) {
        this.out = out
    }

    override fun fetchWeather(location: Location) {
        repository.getWeatherFromApi(location).toList().subscribe(
            { },
            { it.printStackTrace() })
    }

    override fun getWeatherFromDb() {
        repository.getWeatherFromDb().subscribe(
            { out.setItems(it) },
            { it.printStackTrace() })
    }
}