package ru.kolyagin.allweather.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(
    @PrimaryKey val name: String,
    val temp: Double,
    val humidity: Double,
    val windSpeed: Double,
    val status: String,
    val city: String,
    val icon: String,
    val isLoaded: Boolean
)
