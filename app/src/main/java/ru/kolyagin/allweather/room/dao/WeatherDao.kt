package ru.kolyagin.allweather.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import ru.kolyagin.allweather.*
import ru.kolyagin.allweather.room.entity.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: Weather)

    @Query("SELECT * FROM weather")
    fun getAll(): Flowable<List<Weather>>

    @Query("SELECT * FROM weather WHERE name = '$OPEN_WEATHER_NAME'")
    fun getOpenWeather(): Single<Weather>

    @Query("SELECT * FROM weather WHERE name = '$WEATHER_BIT_NAME'")
    fun getWeatherBit(): Single<Weather>

    @Query("SELECT * FROM weather WHERE name = '$WEATHER_API_NAME'")
    fun getWeatherApi(): Single<Weather>

    @Query("SELECT * FROM weather WHERE name = '$WEATHER_TRACK_NAME'")
    fun getWeatherTrack(): Single<Weather>

    @Query("SELECT * FROM weather WHERE name = '$ACCU_WEATHER_NAME'")
    fun getAccuWeather(): Single<Weather>

    @Query("SELECT * FROM weather WHERE name = '$TOMORROW_IO_NAME'")
    fun getTomorrowIo(): Single<Weather>
}