package com.example.mod_ctreino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.mod_ctreino.ui.enums.CurrentScreen
import com.example.mod_ctreino.ui.preferences.AppPreferences
import com.example.mod_ctreino.ui.repository.DataRepository
import com.example.mod_ctreino.ui.screens.LoginScreen
import com.example.mod_ctreino.ui.screens.SplashScreen
import com.example.mod_ctreino.ui.theme.MOD_CTreinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MOD_CTreinoTheme() {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppRoot(){
    var currentScreen by rememberSaveable { mutableStateOf(CurrentScreen.SPLASH) }
    val context = LocalContext.current
    val preferences = AppPreferences(context)
    val repository = DataRepository(LocalContext.current)

    when(currentScreen){
        CurrentScreen.LOGIN -> LoginScreen(onLoginSuccess = {currentScreen = CurrentScreen.SPLASH})

        CurrentScreen.SPLASH -> SplashScreen(
            preferences = preferences,
            splashEnd = { currentScreen = CurrentScreen.LOGIN }
        )
    }
}