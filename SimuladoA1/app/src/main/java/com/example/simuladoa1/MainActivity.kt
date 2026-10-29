package com.example.simuladoa1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.BatteryManager
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.simuladoa1.ui.components.EconomyModeModal
import com.example.simuladoa1.ui.components.LowBatteryModal
import com.example.simuladoa1.ui.components.NoInternetModal
import com.example.simuladoa1.ui.device.HeadphoneObserver
import com.example.simuladoa1.ui.enums.CurrentModal
import com.example.simuladoa1.ui.enums.CurrentScreen
import com.example.simuladoa1.ui.models.DadosCurso
import com.example.simuladoa1.ui.repository.DataRepository
import com.example.simuladoa1.ui.screens.HomeScreen
import com.example.simuladoa1.ui.screens.QuizMyBrainScreen
import com.example.simuladoa1.ui.theme.SimuladoA1Theme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimuladoA1Theme {
                AppRoot()

            }
        }
    }
}


@Composable
fun AppRoot(){

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val activity = context as Activity

    var currentScreen by rememberSaveable() { mutableStateOf(CurrentScreen.HOME) }
    var currentModal by remember { mutableStateOf(CurrentModal.NONE) }

    var lowBattery by remember { mutableStateOf(false) }
    var batteryDismissed by remember { mutableStateOf(false) }

    var economyMode by remember { mutableStateOf(false) }
    var economyDismissed by remember { mutableStateOf(false) }

    var noInternet by remember { mutableStateOf(false) }
    var internetDismissed by remember { mutableStateOf(false) }

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager


    LaunchedEffect(Unit) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        HeadphoneObserver(context).fonesConectados.collect {
            Log.d("Fone", "collect chegou")
            val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                (max*0.5).roundToInt(),
                0
            )

            launch {
                snackbarHostState.showSnackbar(
                    "Fone de ouvido conectado, o volume do dispositivo foi definido como 50%"
                )
            }
        }
    }

    LaunchedEffect(currentScreen) {
        economyMode = powerManager.isPowerSaveMode
    }

    LaunchedEffect(currentScreen) {
        val network = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(network)
        noInternet = caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) != true ||
                (caps?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) != true)
    }

    LaunchedEffect(currentScreen) {
        val batteryIntent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL,-1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE,-1) ?: -1

        if ((level*100)/scale <=20){
            lowBattery = true
        }
    }


    val questoes = remember { mutableStateListOf<DadosCurso>() }

    LaunchedEffect(Unit) {
        questoes.addAll(DataRepository.loadJson(context = context))
    }

    DisposableEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }


    currentModal = when{

        lowBattery && !batteryDismissed -> CurrentModal.LOWBATTERY
        economyMode && !economyDismissed-> CurrentModal.ECONOMY
        noInternet && !internetDismissed -> CurrentModal.NOINTERNET
        else -> CurrentModal.NONE
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){

        when(currentModal){

            CurrentModal.NONE -> {}

            CurrentModal.LOWBATTERY -> LowBatteryModal(
                onDismiss = {
                    batteryDismissed = true
                }
            )

            CurrentModal.NOINTERNET -> NoInternetModal(
                onDismiss = {
                    internetDismissed = true
                }
            )

            CurrentModal.ECONOMY -> EconomyModeModal(
                onDismiss = {
                    economyDismissed = true
                }
            )
        }
        when(currentScreen){

            CurrentScreen.QUIZMYBRAIN -> QuizMyBrainScreen(
                onHome = {currentScreen = CurrentScreen.HOME}
            )

            CurrentScreen.HOME -> {
                LaunchedEffect(Unit) {
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }

                HomeScreen(
                    currentScreen = currentScreen,
                    onHome = { currentScreen = CurrentScreen.HOME },
                    onQuizMyBrain = { currentScreen = CurrentScreen.QUIZMYBRAIN },
                    onGeniusPlay = { currentScreen = CurrentScreen.GENIUSPLAY },
                    onMemoCheck = { currentScreen = CurrentScreen.MEMOCHECK }
                )

            }
            CurrentScreen.GENIUSPLAY -> {}
            CurrentScreen.MEMOCHECK -> {}
        }

        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(),
            hostState = snackbarHostState
        )
    }

}
