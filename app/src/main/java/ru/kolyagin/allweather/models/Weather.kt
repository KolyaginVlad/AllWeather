package ru.kolyagin.allweather.models

data class Weather(
    val name: String,
    val temp: Double,
    val humidity: Double,
    val windSpeed: Double,
    val status: String,
    val city: String,
    val icon: String
)
