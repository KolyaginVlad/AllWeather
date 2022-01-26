package ru.kolyagin.allweather.domain

import android.location.Location
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import ru.kolyagin.allweather.data.MainRepository
import ru.kolyagin.allweather.room.entity.Weather

class MainInteractorImplTest {
    private lateinit var repository: MainRepository
    private lateinit var interactor: MainInteractor
    private lateinit var location: Location
    private lateinit var interactorOut: MainInteractorOut
    private lateinit var observable: Observable<Weather>
    private lateinit var flowable: Flowable<List<Weather>>

    @Before
    fun setUp() {
        repository = mock(MainRepository::class.java)
        interactor = MainInteractorImpl(repository)
        interactorOut = mock(MainInteractorOut::class.java)
        location = mock(Location::class.java)
        observable = mock(Observable::class.java) as Observable<Weather>
        interactor.setupInteractorOut(interactorOut)
        flowable = mock(Flowable::class.java) as Flowable<List<Weather>>

        `when`(repository.getWeatherFromApi(location)).thenReturn(observable)
        `when`(repository.getWeatherFromDb()).thenReturn(flowable)
    }

    @Test
    fun fetchWeather() {
        interactor.fetchWeather(location)
        verify(repository, never()).getWeatherFromDb()
        verify(repository, times(1)).getWeatherFromApi(location)
    }

    @Test
    fun getWeatherFromDb() {
        interactor.getWeatherFromDb()
        verify(repository, times(1)).getWeatherFromDb()
        verify(repository, never()).getWeatherFromApi(location)
    }
}