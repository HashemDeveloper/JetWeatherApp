package com.android.jetweather.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.android.jetweather.BuildConfig
import com.android.jetweather.R
import com.android.jetweather.components.WeatherTopBar
import com.android.jetweather.model.Unit
import com.android.jetweather.screens.settings.SettingsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val unitToggleState = remember { mutableStateOf(false) }
    val measurementUnits = listOf(stringResource(id = R.string.imperial), stringResource(id = R.string.metrics))
    val choiceFromDb = settingsViewModel.units.collectAsState().value
    val defaultChoice: String =
        if (choiceFromDb.isEmpty()) measurementUnits[0] else choiceFromDb[0].unit
    val choiceState = remember { mutableStateOf(defaultChoice) }
    val context = LocalContext.current
    Scaffold(topBar = {
        WeatherTopBar(
            title = "Settings",
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
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.change_unit),
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = !unitToggleState.value,
                    onCheckedChange = { state ->
                        unitToggleState.value = !state
                        if (unitToggleState.value)
                            choiceState.value = context.getString(R.string.imperial) else
                            choiceState.value = context.getString(R.string.metrics)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(0.4f))
                ) {
                    Text(
                        text = if (unitToggleState.value) stringResource(id = R.string.fahrenheit) else stringResource(
                            id = R.string.celcius
                        )
                    )
                }
                Button(
                    onClick = {
                        settingsViewModel.deleteAllUnit()
                        settingsViewModel.insertUnit(Unit(unit = choiceState.value))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}