package com.android.jetweather.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.android.jetweather.model.WeatherInfo
import com.android.jetweather.utils.Constants

@Composable
fun WeatherMainScreen(navController: NavHostController, viewModel: SharedViewModel) {
   WeatherInfoData(viewModel)
}
@Composable
private fun WeatherInfoData(viewModel: SharedViewModel) {
    val isLoading: Boolean? = viewModel.data.value.loading
    val data: WeatherInfo? = viewModel.data.value.data
    val error: Exception? = viewModel.data.value.error
    data?.cod?.let { Log.d(Constants.getTAG("WeatherMainScreen"), it) }
}