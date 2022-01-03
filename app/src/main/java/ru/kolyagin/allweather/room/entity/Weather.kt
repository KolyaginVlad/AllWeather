package ru.kolyagin.allweather.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val name: String,
    val temp: Double,
    val humidity: Double,
    val windSpeed: Double,
    val status: String,
    val city: String,
    val icon: String,
    var isLoaded: Boolean
) {
    companion object {
        fun getEmptyObjectWithName(name: String) = Weather(
            name,
            .0,
            .0,
            .0,
            "",
            "",
            "",
            false
        )
    }
}
