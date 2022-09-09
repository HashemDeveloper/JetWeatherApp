package com.android.jetweather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.jetweather.navigation.ScreenTypes


@Composable
fun WeatherTopBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
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
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )
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
