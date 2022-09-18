package com.android.jetweather.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.jetweather.R
import com.android.jetweather.model.Favorite
import com.android.jetweather.navigation.ScreenTypes
import com.android.jetweather.screens.FavoriteViewModel
import com.android.jetweather.screens.SharedViewModel
import java.util.*


@Composable
fun WeatherTopBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    val showIt = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog, navController)
    }
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddClicked() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Settings Icon"
                    )
                }
            } else {
                Box {}
            }
        },
        navigationIcon = {
            icon?.let { i ->
                Icon(imageVector = icon, contentDescription = icon.name,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked()
                    })
            }
            if (isMainScreen) {
                val isFavoriteItem: List<Favorite> =
                    favoriteViewModel.favList.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])
                    }
                if (isFavoriteItem.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                val splitTitle = title.split(",")
                                val city = splitTitle[0]
                                val country = splitTitle[1]
                                favoriteViewModel.insertFavorite(
                                    Favorite(
                                        city = city,
                                        country = country
                                    )
                                ).run {
                                    showIt.value = true
                                }
                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                } else {
                    showIt.value = false
                    Box {}
                }
                showText(context = context, showIt = showIt)
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
}

@Composable
private fun showText(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, stringResource(id = R.string.favourite_added),
            Toast.LENGTH_SHORT)
            .show()
    }
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var isExpended by remember {
        mutableStateOf(true)
    }
    val settingList = mutableListOf("About", "Favorites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = isExpended, onDismissRequest = {
                isExpended = false
            }, modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            settingList.forEachIndexed { _, text ->
                DropdownMenuItem(onClick = {
                    isExpended = false
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = when (text) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.Favorite
                            else -> Icons.Default.Settings
                        }, contentDescription = "Images",
                        tint = Color.LightGray
                    )
                    Text(text = text, modifier = Modifier.clickable {
                        navController.navigate(
                            when (text) {
                                "About" -> ScreenTypes.ABOUT_SCREEN.name
                                "Favorites" -> ScreenTypes.FAVORITE_SCREEN.name
                                else -> ScreenTypes.SETTINGS_SCREEN.name
                            }
                        )
                    }, fontWeight = FontWeight(300), color = Color.Black)
                }
            }
        }
    }
}
