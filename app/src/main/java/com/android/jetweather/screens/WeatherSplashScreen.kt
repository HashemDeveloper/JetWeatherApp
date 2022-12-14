package com.android.jetweather.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.jetweather.R
import com.android.jetweather.navigation.ScreenTypes
import kotlinx.coroutines.delay


@Composable
fun WeatherSplashScreen(navController: NavHostController) {
    DrawSplashView(navController)
}

@Composable
private fun DrawSplashView(navController: NavHostController? = null) {
    val scale = remember {
        Animatable(0.0f)
    }
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(
            targetValue = 0.9f, animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                })
        )
        delay(2000L)
        val defaultCity = "Brooklyn"
        navController?.navigate(ScreenTypes.MAIN_SCREEN.name + "/$defaultCity")
    })
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .scale(scale.value)
            .size(330.dp), shape = CircleShape,
        color = Color.White, border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun),
                contentDescription = "Sunny Icon", modifier = Modifier.size(95.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = stringResource(id = R.string.find_sun),
                style = MaterialTheme.typography.h5, color = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSplashScreen() {
    DrawSplashView()
}