package com.android.jetweather.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import com.android.jetweather.widgets.DisplayThisWeekWeather
import com.android.jetweather.widgets.HumidityWindPressureRow
import com.android.jetweather.widgets.SunsetSunriseRow

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
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "This Week",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xA6DBDBDB),
            shape = RoundedCornerShape(6.dp)
        ) {
            DisplayThisWeekWeather(weatherInfo)
        }
    }
}
