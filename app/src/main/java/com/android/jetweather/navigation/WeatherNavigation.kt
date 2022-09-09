package com.android.jetweather.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.jetweather.screens.*

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    val viewModel: SharedViewModel = viewModel()
    NavHost(navController = navController, startDestination = ScreenTypes.SPLASH_SCREEN.name) {
        composable(route = ScreenTypes.SPLASH_SCREEN.name) {
            WeatherSplashScreen(navController = navController)
        }
        val route = ScreenTypes.MAIN_SCREEN.name
        composable(route = "${route}/{city}",
        arguments = listOf(navArgument(name = "city") {
            type = NavType.StringType
        })) { navBack ->
            navBack.arguments?.getString("city")?.let {city ->
                WeatherMainScreen(navController = navController,viewModel, city = city)
            }
        }
        composable(route = ScreenTypes.SEARCH_SCREEN.name) {
            SearchScreen(navController = navController)
        }
        composable(route = ScreenTypes.ABOUT_SCREEN.name) {
            AboutScreen(navController = navController)
        }
        composable(route = ScreenTypes.FAVORITE_SCREEN.name) {
            FavoriteScreen(navController = navController)
        }
        composable(route = ScreenTypes.SETTINGS_SCREEN.name) {
            SettingsScreen(navController = navController)
        }
    }
}
