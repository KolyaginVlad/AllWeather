package ru.kolyagin.allweather.domain

import android.location.Location
import androidx.core.os.bundleOf
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import ru.kolyagin.allweather.GET_WEATHER_FROM_API_EVENT
import ru.kolyagin.allweather.GET_WEATHER_FROM_DATABASE_EVENT
import ru.kolyagin.allweather.data.MainRepository

class MainInteractorImpl(private val repository: MainRepository) : MainInteractor {
    private lateinit var out: MainInteractorOut

    override fun setupInteractorOut(out: MainInteractorOut) {
        this.out = out
    }

    override fun fetchWeather(location: Location) {
        repository.getWeatherFromApi(location).toList().subscribe(
            { Firebase.analytics.logEvent(GET_WEATHER_FROM_API_EVENT, bundleOf("list" to it)) },
            { Firebase.crashlytics.recordException(it) })
    }

    override fun getWeatherFromDb() {
        repository.getWeatherFromDb().subscribe(
            {
                out.setItems(it)
                Firebase.analytics.logEvent(GET_WEATHER_FROM_DATABASE_EVENT, bundleOf("list" to it))
            },
            { Firebase.crashlytics.recordException(it) })
    }
}