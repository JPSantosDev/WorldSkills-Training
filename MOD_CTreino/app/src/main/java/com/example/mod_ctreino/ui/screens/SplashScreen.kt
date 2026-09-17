package com.example.mod_ctreino.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mod_ctreino.R
import com.example.mod_ctreino.ui.theme.Typography
import com.example.mod_ctreino.ui.model.AppState
import com.example.mod_ctreino.ui.preferences.AppPreferences
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    preferences: AppPreferences,
    splashEnd: () -> Unit
){

    val context = LocalContext.current
    val state by preferences.state.collectAsStateWithLifecycle(AppState())

    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    LaunchedEffect(state.splashSeen) {
        if (state.splashSeen){
            delay(3000)
            splashEnd()

        }
        else{
            delay(10000)
            preferences.toggleSplashSeen()
            splashEnd()
        }
    }

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(painter = painterResource( R.drawable.ic_launcher_foreground),contentDescription = null, modifier = Modifier.size(180.dp))
        Text("Carregando", style = Typography.headlineSmall, fontWeight = FontWeight.Bold)


    }

}