package ru.kolyagin.allweather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.kolyagin.allweather.*
import ru.kolyagin.allweather.api.*
import ru.kolyagin.allweather.data.MainRepository
import ru.kolyagin.allweather.data.MainRepositoryImpl
import ru.kolyagin.allweather.domain.MainInteractor
import ru.kolyagin.allweather.domain.MainInteractorImpl
import ru.kolyagin.allweather.room.WeatherDatabase
import ru.kolyagin.allweather.room.dao.WeatherDao
import javax.inject.Singleton

@Module
class ContextModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context.applicationContext
}

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherDatabase(context: Context) = WeatherDatabase.buildDatabase(context)

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase) = weatherDatabase.getWeatherDao()
}

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): OpenWeatherApi = Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(OpenWeatherApi::class.java)

}

@Module(includes = [RepositoryModule::class])
class InteractorModule {
    @Provides
    fun provideMainInteractor(repository: MainRepository): MainInteractor =
        MainInteractorImpl(repository)
}

@Module(includes = [DatabaseModule::class])
class RepositoryModule {
    @Provides
    fun provideMainReposytory(weatherDao: WeatherDao): MainRepository =
        MainRepositoryImpl(weatherDao)
}