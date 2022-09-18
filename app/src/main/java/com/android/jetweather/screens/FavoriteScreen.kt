package com.android.jetweather.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.jetweather.BuildConfig
import com.android.jetweather.R
import com.android.jetweather.components.WeatherTopBar
import com.android.jetweather.model.Favorite
import com.android.jetweather.navigation.ScreenTypes
import kotlinx.coroutines.flow.collect

@Composable
fun FavoriteScreen(
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherTopBar(
            title = stringResource(id = R.string.favorite_cities),
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        if (BuildConfig.DEBUG) {
            print(it)
        }
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list: MutableList<Favorite> = favoriteViewModel.favList.collectAsState().value
                LazyColumn {
                    items(list) { favList ->
                        CityRow(favList, navController = navController, favoriteViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favList: Favorite,
    navController: NavHostController,
    favoriteViewModel: FavoriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(ScreenTypes.MAIN_SCREEN.name + "/${favList.city}")
            }, shape = CircleShape.copy(topEnd = CornerSize(6.dp)), color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favList.city, modifier = Modifier.padding(start = 4.dp))
            Surface(
                modifier = Modifier.padding(0.dp), shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = favList.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Favorite Icon", modifier = Modifier.clickable {
                favoriteViewModel.deleteFavorite(favList)
            }, tint = Color.Red.copy(alpha = 0.3f))
        }
    }
}
