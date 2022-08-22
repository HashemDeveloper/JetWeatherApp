package com.android.jetweather.network

import com.android.jetweather.model.WeatherInfo
import com.android.jetweather.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeatherInfo(
        @Query("q") location: String,
        @Query("appId") appId: String = Constants.API_KEY,
        @Query("units") units: String = "imperial"
    ): WeatherInfo
}