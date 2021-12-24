package ru.kolyagin.allweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kolyagin.allweather.models.Weather
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
    fun change() {
        items.value = items.value?.subList(0, 2)
    }

    val items = MutableLiveData<List<Weather>>(listOf(
        Weather(
            "OpenWeather",
            22.0,
            0.69,
            10.0,
            "Ясно",
            "Пенза",
            "https://img2.freepng.ru/20180328/gfe/kisspng-weather-forecasting-computer-icons-meteorology-sun-5abb727a6ffa17.2462013815222339784587.jpg"
        ),
        Weather(
            "Weatherbit",
            23.0,
            0.70,
            11.0,
            "Ясно",
            "Пенза",
            "https://img2.freepng.ru/20180328/gfe/kisspng-weather-forecasting-computer-icons-meteorology-sun-5abb727a6ffa17.2462013815222339784587.jpg"
        ),
        Weather(
            "AccuWeather",
            22.5,
            0.65,
            10.2,
            "Ясно",
            "Пенза",
            "https://img2.freepng.ru/20180328/gfe/kisspng-weather-forecasting-computer-icons-meteorology-sun-5abb727a6ffa17.2462013815222339784587.jpg"
        ),
        Weather(
            "Tomorrow.io",
            21.9,
            0.67,
            10.3,
            "Ясно",
            "Пенза",
            "https://img2.freepng.ru/20180328/gfe/kisspng-weather-forecasting-computer-icons-meteorology-sun-5abb727a6ffa17.2462013815222339784587.jpg"
        ),
        Weather(
            "WeatherApi",
            22.2,
            0.69,
            10.5,
            "Ясно",
            "Пенза",
            "https://img2.freepng.ru/20180328/gfe/kisspng-weather-forecasting-computer-icons-meteorology-sun-5abb727a6ffa17.2462013815222339784587.jpg"
        ),
        Weather(
            "WeatherTrack",
            22.3,
            0.66,
            10.7,
            "Ясно",
            "Пенза",
            "https://img2.freepng.ru/20180328/gfe/kisspng-weather-forecasting-computer-icons-meteorology-sun-5abb727a6ffa17.2462013815222339784587.jpg"
        )
    ))
}