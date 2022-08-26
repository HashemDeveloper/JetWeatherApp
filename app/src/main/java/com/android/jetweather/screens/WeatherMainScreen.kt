package com.android.jetweather.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jetweather.BuildConfig
import com.android.jetweather.R
import com.android.jetweather.components.WeatherStateImage
import com.android.jetweather.components.WeatherTopBar
import com.android.jetweather.model.WeatherInfo
import com.android.jetweather.model.WeatherObj
import com.android.jetweather.utils.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherMainScreen(navController: NavHostController, viewModel: SharedViewModel) {
    WeatherInfoData(viewModel)
}

@Composable
private fun WeatherInfoData(viewModel: SharedViewModel) {
    val isLoading: Boolean? = viewModel.data.value.loading
    val data: WeatherInfo? = viewModel.data.value.data
    val error: Exception? = viewModel.data.value.error

    isLoading?.let { loading ->
        if (loading) {
            CircularProgressIndicator()
        } else {
            data?.let { d ->
                MainScaffold(d)
            }
        }
    }
}

@Composable
private fun MainScaffold(weatherInfo: WeatherInfo? = null) {
    Scaffold(topBar = {
        WeatherTopBar(
            title = weatherInfo?.city?.name + ", ${weatherInfo?.city?.country}",
            elevation = 5.dp
        ) {
            Log.d(Constants.getTAG(""), "Button Clicked")
        }
    }) {
        if (BuildConfig.DEBUG) {
            print(it)
        }
        MainContent(weatherInfo)
    }
}

@Composable
fun MainContent(weatherInfo: WeatherInfo? = null) {
    val imageUrl =
        "https://openweathermap.org/img/wn/${weatherInfo?.list?.first()?.weather?.first()?.icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherInfo?.list?.first()?.dt!!),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp), shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherInfo.list.first().temp.day) + "º",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                weatherInfo.list.first().weather.first().main.let {
                    Text(
                        text = it,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
        HumidityWindPressureRow(weatherInfo)
        Divider()
        SunsetSunriseRow(weatherInfo)
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text(text = "This Week", style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold)
        }
        Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xA6DBDBDB)) {
            DisplayThisWeekWeather(weatherInfo)
        }
    }
}

@Composable
fun DisplayThisWeekWeather(weatherInfo: WeatherInfo) {
    LazyColumn(content = {
        items(items = weatherInfo.list) { days ->
            WeatherCard(days)
        }
    })
}

@Preview
@Composable
fun WeatherCard(weatherObj: WeatherObj? = null) {
    var date = -1
    var iconUrl = ""
    var desc = ""
    var deg = ""
    weatherObj?.weather?.first()?.icon?.let {
        iconUrl = iconUrl(it)
    }
    weatherObj?.let {
        date = it.dt
    }
    weatherObj?.weather?.first()?.let {
        desc = it.description
    }
    deg = weatherObj?.temp?.day?.let { formatDecimals(it) } + "º"
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(50.dp), elevation = 4.dp,
        shape = RoundedCornerShape(
            topStartPercent = 50,
            bottomEndPercent = 50,
            bottomStartPercent = 50
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = formatDayOfTheWeek(date))
            WeatherStateImage(imageUrl = iconUrl)
            Card(
                modifier = Modifier.padding(4.dp),
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color(0xFFFFC400)
            ) {
                Text(
                    text = desc
                )
            }
            Text(
                text = deg, style = MaterialTheme.typography.h6,
                color = Color(0xFF03A9F4)
            )
        }
    }
}

@Composable
fun SunsetSunriseRow(weatherInfo: WeatherInfo) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weatherInfo.list.first().sunrise),
                style = MaterialTheme.typography.caption
            )
        }
        Row {
            Text(
                text = formatDateTime(weatherInfo.list.first().sunset),
                style = MaterialTheme.typography.caption
            )
            Icon(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset icon",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}

@Composable
fun HumidityWindPressureRow(weatherInfo: WeatherInfo?) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weatherInfo?.list?.first()?.humidity}",
                style = MaterialTheme.typography.caption
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weatherInfo?.list?.first()?.pressure} psi",
                style = MaterialTheme.typography.caption
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weatherInfo?.list?.first()?.humidity} mph",
                style = MaterialTheme.typography.caption
            )
        }
    }
}
