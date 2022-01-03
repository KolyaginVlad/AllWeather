package ru.kolyagin.allweather.di

import dagger.Component
import ru.kolyagin.allweather.data.MainRepository
import ru.kolyagin.allweather.data.MainRepositoryImpl
import ru.kolyagin.allweather.presentation.MainViewModel
import ru.kolyagin.allweather.room.dao.WeatherDao
import javax.inject.Singleton

@Singleton
@Component(modules = [InteractorModule::class])
interface ViewModelComponent {
    fun getMainViewModel(): MainViewModel
}

@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(repository: MainRepositoryImpl)
}


@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun getWeatherDao(): WeatherDao
}