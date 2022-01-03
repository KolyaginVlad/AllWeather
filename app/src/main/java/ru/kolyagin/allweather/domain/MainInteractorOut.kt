package ru.kolyagin.allweather.domain

import ru.kolyagin.allweather.room.entity.Weather

interface MainInteractorOut {
    fun setItems(list: List<Weather>)

}
