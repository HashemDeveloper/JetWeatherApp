package com.android.jetweather.repository.remote

import android.util.Log
import com.android.jetweather.BuildConfig
import com.android.jetweather.data.DataOrException
import com.android.jetweather.model.WeatherInfo
import com.android.jetweather.network.WeatherAPI
import com.android.jetweather.utils.Constants
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherAPI) {
    private val dataOrException: DataOrException<WeatherInfo,Boolean,Exception> = DataOrException()

    suspend fun getWeather(city: String): DataOrException<WeatherInfo,Boolean,Exception> {
        try {
            this.dataOrException.loading = true
            this.dataOrException.data = this.api.getWeatherInfo(city)
            if (this.dataOrException.data?.cod?.isNotEmpty() == true) {
                this.dataOrException.loading = false
            }
        } catch (e: Exception) {
            this.dataOrException.loading = false
            this.dataOrException.error = e
            if (BuildConfig.DEBUG) {
                e.localizedMessage?.let { Log.d(Constants.getTAG(WeatherRepository::class), it) }
            }
        }
        return this.dataOrException
    }
}