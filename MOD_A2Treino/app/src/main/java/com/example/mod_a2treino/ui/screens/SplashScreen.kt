package com.example.mod_a2treino.ui.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.mod_a2treino.R
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(onSplashEnd: () -> Unit) {

    val activity = LocalActivity.current
    val view = LocalView.current
    val window = activity?.window


    LaunchedEffect(Unit) {
        delay(2000.milliseconds)
        onSplashEnd()
    }

    DisposableEffect(window,view) {

        if (window == null) return@DisposableEffect onDispose {  }

        val controller = WindowCompat.getInsetsController(window,view)
        val oldBehavior = controller.systemBarsBehavior

        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        controller.hide(WindowInsetsCompat.Type.systemBars())


        onDispose {
            controller.systemBarsBehavior = oldBehavior
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun PreSplash(){
    SplashScreen(
        onSplashEnd = {}
    )
}