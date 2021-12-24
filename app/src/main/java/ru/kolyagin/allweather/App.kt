package ru.kolyagin.allweather

import android.app.Application
import dagger.Component

class App:Application() {
    override fun onCreate() {
        super.onCreate()

    }
}
@Component
interface ViewModelComponent {
    fun getMainViewModel(): MainViewModel
}