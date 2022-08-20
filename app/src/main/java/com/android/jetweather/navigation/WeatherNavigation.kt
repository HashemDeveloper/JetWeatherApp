package com.android.jetweather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenTypes.SPLASH_SCREEN.name) {
        composable(route = ScreenTypes.SPLASH_SCREEN.name) {
            WeatherSplashScreen(navController = navController)
        }
    }
}
