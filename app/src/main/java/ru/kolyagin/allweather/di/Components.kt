package ru.kolyagin.allweather.di

import dagger.Component
import ru.kolyagin.allweather.api.OpenWeatherApi
import ru.kolyagin.allweather.presentation.MainViewModel
import ru.kolyagin.allweather.room.dao.WeatherDao
import javax.inject.Singleton

@Component
interface ViewModelComponent {
    fun getMainViewModel(): MainViewModel
}

@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(mainViewModel: MainViewModel)
}


@Singleton
@Component(modules = [DatabaseModule::class])
interface SingleComponent {
    fun getWeatherDao(): WeatherDao
}