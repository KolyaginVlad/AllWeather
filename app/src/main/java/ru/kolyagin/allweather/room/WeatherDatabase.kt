package ru.kolyagin.allweather.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kolyagin.allweather.DATABASE_NAME
import ru.kolyagin.allweather.room.dao.WeatherDao
import ru.kolyagin.allweather.room.entity.Weather

@Database(entities = [Weather::class], version = 1, exportSchema = true)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}