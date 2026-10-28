package com.example.mod_a2treino

import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mod_a2treino.ui.enums.CurrentScreen
import com.example.mod_a2treino.ui.models.AppPreferences
import com.example.mod_a2treino.ui.models.School
import com.example.mod_a2treino.ui.preferences.DataPreferences
import com.example.mod_a2treino.ui.repository.ApiClient
import com.example.mod_a2treino.ui.screens.DetailScreen
import com.example.mod_a2treino.ui.screens.HomeScreen
import com.example.mod_a2treino.ui.screens.ListScreen
import com.example.mod_a2treino.ui.screens.LoginScreen
import com.example.mod_a2treino.ui.screens.MapScreen
import com.example.mod_a2treino.ui.screens.SplashScreen
import com.example.mod_a2treino.ui.theme.MOD_A2TreinoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val screenCaptureCallback =
        ScreenCaptureCallback{
            runOnUiThread {
                Toast.makeText(this,"Captura de tela detectada",Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(UPSIDE_DOWN_CAKE)
    override fun onStart() {
        super.onStart()
        registerScreenCaptureCallback(
            mainExecutor,
            screenCaptureCallback
        )
    }

    @RequiresApi(UPSIDE_DOWN_CAKE)
    override fun onStop() {
        unregisterScreenCaptureCallback(screenCaptureCallback)
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MOD_A2TreinoTheme {
                AppScreens()
            }
        }
    }
}

@Composable
fun AppScreens() {

    val context = LocalContext.current
    val activity = LocalActivity.current

    var currentScreen by remember { mutableStateOf(CurrentScreen.SPLASH) }
    val preferences = DataPreferences(context)
    val state by preferences.state.collectAsStateWithLifecycle(AppPreferences())
    val scope = rememberCoroutineScope()
    var detailId by remember { mutableIntStateOf(0) }
    val escolas = remember { mutableStateListOf<School>() }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        escolas.addAll(ApiClient.getSchoolList().escolas)
        loading = false
    }

    when (currentScreen) {

        CurrentScreen.SPLASH -> {
            SplashScreen(
                onSplashEnd = { currentScreen = CurrentScreen.LOGIN }
            )
        }

        CurrentScreen.LOGIN -> {
            LoginScreen(
                onLogin = { scope.launch { preferences.toggleFirstLogin() }
                currentScreen = CurrentScreen.HOME

                },
                context = context,
                )
        }

        CurrentScreen.LIST_SCHOOL -> {
            ListScreen(

                context = context,
                escolas = escolas,
                onClick = {
                    detailId = it
                    currentScreen = CurrentScreen.DETAIL

                },
                onLogout = { currentScreen = CurrentScreen.LOGIN }
            )
        }
        CurrentScreen.MAPA -> {
            DisposableEffect(Unit) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                onDispose {
                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }
            }

            MapScreen(
                context = context,
                onLogout = { currentScreen = CurrentScreen.LOGIN }
            )
        }

        CurrentScreen.DETAIL -> {
            DetailScreen(
                escolas = escolas,
                onBack = {
                    currentScreen = CurrentScreen.LIST_SCHOOL
                },
                id = detailId
            )
        }

        CurrentScreen.HOME -> HomeScreen(
            context = context,
            onLogout = { currentScreen = CurrentScreen.LOGIN },
            currentScreen = currentScreen,
            onMapa = { currentScreen = CurrentScreen.MAPA }
        )
    }
}
