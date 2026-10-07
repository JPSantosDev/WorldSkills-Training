package com.example.mod_ctreino

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mod_ctreino.ui.enums.CurrentScreen
import com.example.mod_ctreino.ui.model.AppState
import com.example.mod_ctreino.ui.preferences.AppPreferences
import com.example.mod_ctreino.ui.repository.DataRepository
import com.example.mod_ctreino.ui.screens.ArtigoScreen
import com.example.mod_ctreino.ui.screens.HomeScreen
import com.example.mod_ctreino.ui.screens.LoginScreen
import com.example.mod_ctreino.ui.screens.PerfilScreen
import com.example.mod_ctreino.ui.screens.SplashScreen
import com.example.mod_ctreino.ui.theme.MOD_CTreinoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppRoot()
        }
    }
}

@Composable
fun AppRoot() {
    var uri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val preferences = remember { AppPreferences(context) }
    val repository = remember { DataRepository(context) }
    val appState by preferences.state.collectAsStateWithLifecycle(initialValue = AppState())
    val coroutineScope = rememberCoroutineScope()

    var currentScreen by rememberSaveable { mutableStateOf(CurrentScreen.HOME) }

    MOD_CTreinoTheme(
        darkTheme = appState.darkMode,
        highContrast = appState.highContrast,
        dynamicColor = false
    ) {
        when (currentScreen) {
            CurrentScreen.HOME -> HomeScreen(
                onHome = { currentScreen = CurrentScreen.HOME },
                onExplore = { },
                onExercises = { },
                onArticles = { currentScreen = CurrentScreen.ARTIGO },
                onPerfil = {currentScreen = CurrentScreen.PERFIL },
                repository = repository
            )

            CurrentScreen.LOGIN -> LoginScreen(
                onLoginSuccess = { currentScreen = CurrentScreen.SPLASH }
            )

            CurrentScreen.SPLASH -> SplashScreen(
                preferences = preferences,
                splashEnd = { currentScreen = CurrentScreen.HOME }
            )

            CurrentScreen.ARTIGO -> ArtigoScreen(
                onBack = { currentScreen = CurrentScreen.HOME },
                onCompartilhar = { },
                onQrCode = { },
                onThemeSwitch = {
                    coroutineScope.launch {
                        preferences.toggleDarkMode()
                    }
                },
                onFontChange = { },
                onOuvir = { }
            )
            CurrentScreen.PERFIL -> PerfilScreen(
                uri = uri,
                onUriChange = { uri = it }
            )
        }
    }
}
