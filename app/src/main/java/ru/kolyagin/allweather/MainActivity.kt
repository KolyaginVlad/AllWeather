package ru.kolyagin.allweather

import android.os.Bundle
import android.os.Handler
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import ru.kolyagin.allweather.composables.Pager
import ru.kolyagin.allweather.models.Weather
import ru.kolyagin.allweather.ui.theme.AllWeatherTheme
import ru.kolyagin.allweather.ui.theme.Purple700

class MainActivity : ComponentActivity() {
    private val viewModel = DaggerViewModelComponent.create().getMainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.items.observe(this){
            setContent {
                AllWeatherTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        MainContent(0, items = it)
                    }
                }
            }
        }
    }
}



@Composable
fun MainContent(initialIndex: Int, items: List<Weather>) {
    Pager(
        items = items,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        itemFraction = 1f,
        initialIndex = initialIndex,
        itemSpacing = 0.dp,
        contentFactory = { item ->
            Page(weather = item)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AllWeatherTheme {
        MainContent(0, items = emptyList())
    }
}

@Composable
fun Page(weather: Weather) {
    val scrollState = rememberScrollState()
    Scaffold(topBar = {
        TopAppBar(title = { Text(weather.name) }, backgroundColor = Purple700)
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
            )
            Spacer(modifier = Modifier.size(16.dp))
            Image(
                painter = rememberImagePainter(data = weather.icon),
                contentDescription = "icon",
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = weather.status,
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(id = R.string.speedWind)
                        + ": ${weather.windSpeed} " + stringResource(
                    id = R.string.meterPerSecond
                ),
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = stringResource(id = R.string.humidity)
                        + ": ${weather.humidity * 100}%",
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Gray),
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = weather.city,
                maxLines = 2,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
            )

        }

    }
}