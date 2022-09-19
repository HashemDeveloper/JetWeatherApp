package com.android.jetweather.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.jetweather.R
import com.android.jetweather.components.WeatherStateImage
import com.android.jetweather.model.WeatherInfo
import com.android.jetweather.model.WeatherObj
import com.android.jetweather.utils.formatDateTime
import com.android.jetweather.utils.formatDayOfTheWeek
import com.android.jetweather.utils.formatDecimals
import com.android.jetweather.utils.iconUrl

@Composable
fun DisplayThisWeekWeather(weatherInfo: WeatherInfo) {
    LazyColumn(content = {
        items(items = weatherInfo.list) { days ->
            WeatherCard(days)
        }
    }, contentPadding = PaddingValues(1.dp))
}

@Preview
@Composable
private fun WeatherCard(weatherObj: WeatherObj? = null) {
    var date = -1
    var iconUrl = ""
    var desc = ""
    weatherObj?.weather?.first()?.icon?.let {
        iconUrl = iconUrl(it)
    }
    weatherObj?.let {
        date = it.dt
    }
    weatherObj?.weather?.first()?.let {
        desc = it.description
    }
    val maxDeg: String = weatherObj?.temp?.max?.let { formatDecimals(it) } + "º"
    val minDeg: String = weatherObj?.temp?.min?.let { formatDecimals(it) } + "º"
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
                    text = desc,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ) {
                    append(maxDeg)
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray
                )
                ) {
                    append(minDeg)
                }
            })
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
fun HumidityWindPressureRow(weatherInfo: WeatherInfo?, isImperial: Boolean) {
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
                text = "${weatherInfo?.list?.first()?.speed?.let { formatDecimals(it) }}${if (isImperial) " mph" else " m/s"}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}