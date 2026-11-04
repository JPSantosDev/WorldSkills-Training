package com.example.a1ateondeder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.a1ateondeder.ui.theme.A1AteOndeDerTheme
import com.example.a1ateondeder.ui.theme.components.ConnectionDialog
import com.example.a1ateondeder.ui.theme.components.LowBatteryDialog
import com.example.a1ateondeder.ui.theme.components.PowerSaveDialog
import com.example.a1ateondeder.ui.theme.device.BatteryObserver
import com.example.a1ateondeder.ui.theme.device.NetworkObserver
import com.example.a1ateondeder.ui.theme.device.PowerObserver
import com.example.a1ateondeder.ui.theme.enums.CurrentDialog
import com.example.a1ateondeder.ui.theme.enums.CurrentScreen
import com.example.a1ateondeder.ui.theme.models.Pergunta
import com.example.a1ateondeder.ui.theme.repository.DataRepository
import com.example.a1ateondeder.ui.theme.screens.HomeScreen
import com.example.a1ateondeder.ui.theme.screens.QuizMyBrainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A1AteOndeDerTheme {
                AppRoot()

            }
        }
    }
}


@Composable
fun AppRoot(){

    val context = LocalContext.current


    val questoes = remember { mutableStateListOf<Pergunta>() }

    var loading by remember { mutableStateOf(false) }

    val powerObserver = remember { PowerObserver(context) }
    val isPowerSaveMode = powerObserver.isPowerSave.collectAsStateWithLifecycle(false)
    var showPowerDialog by remember { mutableStateOf(false) }

    val networkObserver = remember { NetworkObserver(context) }
    val isConnected = networkObserver.isConnectedWithInternet.collectAsStateWithLifecycle(true)
    var showNetworkDialog by remember { mutableStateOf(false) }

    val batteryObserver = remember { BatteryObserver(context) }
    val isLowBattery = batteryObserver.isLowBattery.collectAsStateWithLifecycle(false)
    var showBatteryDialog by remember { mutableStateOf(false) }

    var currentDialog by remember { mutableStateOf(CurrentDialog.NONE)}
    var currentScreen by remember { mutableStateOf(CurrentScreen.HOME) }


    LaunchedEffect(Unit) {
        loading = true
        questoes.addAll(DataRepository.loadJson(context).perguntas)
        loading = false
    }

    LaunchedEffect(isLowBattery) {
        if (isLowBattery.value) {
            showBatteryDialog = true
        }
    }


    LaunchedEffect(isConnected) {
        if (!isConnected.value) {
            showNetworkDialog = true
        }
    }


    LaunchedEffect(isPowerSaveMode) {
        if (isPowerSaveMode.value) {
            showPowerDialog = true
        }
    }

    currentDialog = when {
        showBatteryDialog -> CurrentDialog.BATTERY
        showNetworkDialog -> CurrentDialog.INTERNET
        showPowerDialog -> CurrentDialog.POWER
        else -> CurrentDialog.NONE
    }

    when(currentDialog) {

        CurrentDialog.BATTERY -> {
            LowBatteryDialog(
                onDismiss = {
                    showBatteryDialog = false
                }
            )
        }

        CurrentDialog.POWER -> {
            PowerSaveDialog(
                onDismiss = {showPowerDialog = false}
            )
        }

        CurrentDialog.INTERNET -> {
            ConnectionDialog()
        }

        CurrentDialog.NONE -> {}
    }

    when(currentScreen){
        CurrentScreen.HOME -> HomeScreen (
            currentScreen = currentScreen,
            onHome = { currentScreen = CurrentScreen.HOME },
            onQuiz = { currentScreen = CurrentScreen.QUIZMYBRAIN },
            onGenius = { currentScreen = CurrentScreen.GENIUSPLAY },
            onMemoCheck = { currentScreen = CurrentScreen.MEMOCHECK },
        )

        CurrentScreen.QUIZMYBRAIN -> QuizMyBrainScreen(
            questoes = questoes,
        )

        CurrentScreen.GENIUSPLAY -> TODO()

        CurrentScreen.MEMOCHECK -> TODO()

    }

    if (loading){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()

        ){
            Column(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text("Carregando")
            }
        }
    }
}

