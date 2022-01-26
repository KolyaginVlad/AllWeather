package ru.kolyagin.allweather.presentation

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*
import ru.kolyagin.allweather.domain.MainInteractor
import ru.kolyagin.allweather.room.entity.Weather

class MainViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private lateinit var interactor: MainInteractor
    private lateinit var viewModel: MainViewModel
    private lateinit var location: Location
    private lateinit var list: List<Weather>

    @Before
    fun setUp() {
        interactor = mock(MainInteractor::class.java)
        viewModel = MainViewModel(interactor)
        location = mock(Location::class.java)
        list = listOf(
            Weather.getEmptyObjectWithName("Some Name"), Weather(
                "name",
                .0,
                .0,
                .0,
                "status",
                "Random City",
                "https://...",
                isLoaded = false
            )
        )
    }

    @Test
    fun setItems() = runBlocking(Dispatchers.IO) {
        viewModel.setItems(list)
        viewModel.items.observeForever {
            assertEquals(list.size, it.size)
            it.forEachIndexed { index, weather ->
                assertEquals(list[index].hashCode(), weather.hashCode())
            }
        }
    }

    @Test
    fun update() {
        viewModel.update(location)
        verify(interactor, times(1)).fetchWeather(location)
        verify(interactor, never()).getWeatherFromDb()
    }

    @Test
    fun initItems() {
        viewModel.initItems()
        verify(interactor, times(1)).getWeatherFromDb()
        verify(interactor, never()).fetchWeather(location)
    }
}