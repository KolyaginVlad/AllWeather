package ru.kolyagin.allweather.domain

import android.location.Location
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import ru.kolyagin.allweather.data.MainRepository

class MainInteractorImpl(private val repository: MainRepository) : MainInteractor {
    private lateinit var out: MainInteractorOut

    override fun setupInteractorOut(out: MainInteractorOut) {
        this.out = out
    }

    override fun fetchWeather(location: Location) {
        repository.getWeatherFromApi(location).toList().subscribe(
            { },
            { Firebase.crashlytics.recordException(it) })
    }

    override fun getWeatherFromDb() {
        repository.getWeatherFromDb().subscribe(
            { out.setItems(it) },
            { Firebase.crashlytics.recordException(it) })
    }
}