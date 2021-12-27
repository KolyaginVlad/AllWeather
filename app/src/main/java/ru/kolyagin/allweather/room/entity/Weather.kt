package ru.kolyagin.allweather.room.entity

import androidx.room.Entity

@Entity
data class Weather(
    val name: String,
    val temp: Double,
    val humidity: Double,
    val windSpeed: Double,
    val status: String,
    val city: String,
    val icon: String,
    val isLoaded: Boolean
)
