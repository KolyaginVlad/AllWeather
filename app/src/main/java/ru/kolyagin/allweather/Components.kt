package ru.kolyagin.allweather

import dagger.Component
import javax.inject.Singleton

@Component
interface ViewModelComponent {
    fun getMainViewModel(): MainViewModel
}

@Component
@Singleton
interface SingleComponent{
    fun getRetrofitHelper(): RetrofitHelper
}