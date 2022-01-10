package ru.kolyagin.allweather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.kolyagin.allweather.R
import ru.kolyagin.allweather.composables.Pager
import ru.kolyagin.allweather.di.ContextModule
import ru.kolyagin.allweather.di.DaggerViewModelComponent
import ru.kolyagin.allweather.room.entity.Weather
import ru.kolyagin.allweather.ui.theme.AllWeatherTheme
import ru.kolyagin.allweather.ui.theme.Purple700
import ru.kolyagin.allweather.utils.*


class MainActivity : ComponentActivity() {
    private val viewModel by lazy {
        DaggerViewModelComponent.builder().contextModule(ContextModule(this)).build()
            .getMainViewModel()
    }
    private val locationHelper by lazy { LocationHelper(this, viewModel::update) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initItems()
        viewModel.items.observe(this) {
            setContent {
                AllWeatherTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        MainContent(getPage(), items = it) {
                            savePage(it)
                        }
                    }
                }
            }
        }
        if (isNeedToUpdate())
            locationHelper.checkPermissionAndGetLocation()
        updateLastLoginDateAndLang()
    }

}

@Composable
fun MainContent(initialIndex: Int, items: List<Weather>, pageChanged: (Int) -> Unit) {
    Pager(
        items = items,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        itemFraction = 1f,
        initialIndex = initialIndex,//FIXME не работает т.к. при обновлении списка сбрасывает положение, но не активирует функцию сдвига
        itemSpacing = 0.dp,
        overshootFraction = 0.01f,
        onPageChanged = pageChanged,
        contentFactory = { item ->
            if (item.isLoaded) {
                Page(weather = item)
            } else {
                EmptyPage(weather = item)
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    AllWeatherTheme {
//        MainContent(0, items = emptyList())
//    }
//}

@Composable
fun EmptyPage(weather: Weather) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(weather.name) }, backgroundColor = Purple700)
    }) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun Page(weather: Weather) {
    val scrollState = rememberScrollState()
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(weather.name, fontFamily = FontFamily(Font(R.font.top))) },
            backgroundColor = Purple700
        )
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(state = scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.temp) + ":\n${weather.temp}" + stringResource(id = R.string.tempIdent),
                maxLines = 2,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 36.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.top))
            )
            Spacer(modifier = Modifier.size(16.dp))
            Image(
                painter = rememberImagePainter(data = weather.icon),
                contentDescription = "icon",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = weather.status,
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.top))
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.speedWind)
                        + ": ${weather.windSpeed} " + stringResource(
                    id = R.string.meterPerSecond
                ),
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.top))
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.humidity)
                        + ": ${weather.humidity}%",
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
                fontSize = 26.sp,
                fontFamily = FontFamily(Font(R.font.top))
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = weather.city,
                maxLines = 2,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.top))
            )

        }

    }
}