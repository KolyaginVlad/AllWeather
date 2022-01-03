package ru.kolyagin.allweather.presentation

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kolyagin.allweather.domain.MainInteractor
import ru.kolyagin.allweather.domain.MainInteractorOut
import ru.kolyagin.allweather.room.entity.Weather
import javax.inject.Inject

class MainViewModel @Inject constructor(private val interactor: MainInteractor) : ViewModel(),
    MainInteractorOut {
    init {
        interactor.setupInteractorOut(this)
    }

    val items = MutableLiveData<List<Weather>>()
    fun update(location: Location) {
        interactor.fetchWeather(location)
    }

    fun initItems() {
        interactor.getWeatherFromDb()
    }

    override fun setItems(list: List<Weather>) {
        items.postValue(list)
    }

}