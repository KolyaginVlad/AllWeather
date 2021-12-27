package ru.kolyagin.allweather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.kolyagin.allweather.API_START
import ru.kolyagin.allweather.api.*
import ru.kolyagin.allweather.room.WeatherDatabase
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
    private val retrofit = Retrofit.Builder()
        .baseUrl(API_START)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): OpenWeatherApi = retrofit.create(OpenWeatherApi::class.java)

    @Provides
    @Singleton
    fun provideAccuWeatherApi(): AccuWeatherApi = retrofit.create(AccuWeatherApi::class.java)

    @Provides
    @Singleton
    fun provideTomorrowIoApi(): TomorrowIoApi = retrofit.create(TomorrowIoApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherApiApi(): WeatherApiApi = retrofit.create(WeatherApiApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherbitApi(): WeatherbitApi = retrofit.create(WeatherbitApi::class.java)

    @Provides
    @Singleton
    fun provideWeatherTrack(): WeatherTrack = retrofit.create(WeatherTrack::class.java)

}