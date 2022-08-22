package com.android.jetweather.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.jetweather.screens.SharedViewModel
import com.android.jetweather.screens.WeatherMainScreen
import com.android.jetweather.screens.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    val viewModel: SharedViewModel = viewModel()
    NavHost(navController = navController, startDestination = ScreenTypes.SPLASH_SCREEN.name) {
        composable(route = ScreenTypes.SPLASH_SCREEN.name) {
            WeatherSplashScreen(navController = navController)
        }
        composable(route = ScreenTypes.MAIN_SCREEN.name) {
            WeatherMainScreen(navController = navController,viewModel)
        }
    }
}
