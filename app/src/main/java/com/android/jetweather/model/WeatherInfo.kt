package com.android.jetweather.model

data class WeatherInfo(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherObj>,
    val message: Double
)