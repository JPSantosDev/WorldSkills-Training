package com.example.simuladoa2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.simuladoa2.ui.enums.CurrentScreen
import com.example.simuladoa2.ui.screens.HomeScreen
import com.example.simuladoa2.ui.screens.LoginScreen
import com.example.simuladoa2.ui.screens.SplashScreen
import com.example.simuladoa2.ui.theme.SimuladoA2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimuladoA2Theme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot(){

    var currentScreen by remember { mutableStateOf(CurrentScreen.LOGIN) }

    val context = LocalContext.current
    val activity = LocalActivity.current

    when(currentScreen) {

        CurrentScreen.SPLASH -> SplashScreen(
            onSplashEnd = {
                currentScreen = CurrentScreen.LOGIN
            }
        )
        CurrentScreen.LOGIN -> LoginScreen(
            context = context,
            onLogin = { currentScreen = CurrentScreen.HOME }
        )

        CurrentScreen.HOME -> HomeScreen(
            context = context,
            currentScreen = currentScreen,
            onSchoolList = { currentScreen = CurrentScreen.LIST_ESCOLA },
            onMap = { currentScreen = CurrentScreen.MAP },
            onLogout = { currentScreen = CurrentScreen.LOGIN },
        )

        CurrentScreen.LIST_ESCOLA ->{}
        CurrentScreen.DETAIL_ESCOLA -> {}
        CurrentScreen.MAP -> {}

    }
}