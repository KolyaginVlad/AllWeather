package ru.kolyagin.allweather.room.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Flowable
import ru.kolyagin.allweather.room.entity.Weather

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllWeather(vararg weather: Weather)

    @Query("SELECT * FROM weather")
    fun getAll() : Flowable<List<Weather>>
}