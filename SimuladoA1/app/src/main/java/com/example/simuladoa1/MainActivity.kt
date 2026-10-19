package com.example.simuladoa1

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.simuladoa1.ui.components.LowBatteryModal
import com.example.simuladoa1.ui.enums.CurrentModal
import com.example.simuladoa1.ui.enums.CurrentScreen
import com.example.simuladoa1.ui.models.DadosCurso
import com.example.simuladoa1.ui.repository.DataRepository
import com.example.simuladoa1.ui.screens.HomeScreen
import com.example.simuladoa1.ui.screens.QuizMyBrainScreen
import com.example.simuladoa1.ui.theme.SimuladoA1Theme

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

    val context = LocalContext.current
    val activity = context as Activity

    var currentScreen by remember { mutableStateOf(CurrentScreen.HOME) }
    var currentModal by remember { mutableStateOf(CurrentModal.NONE) }

    var lowBattery by remember { mutableStateOf(false) }
    var economyMode by remember { mutableStateOf(false) }
    var noInternet by remember { mutableStateOf(false) }




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




    fun verificarModal(){
        currentModal = when {
            lowBattery -> CurrentModal.LOWBATTERY
            economyMode -> CurrentModal.ECONOMY
            noInternet -> CurrentModal.NOINTERNET
            else -> CurrentModal.NONE
        }
    }

    when(currentModal){

        CurrentModal.NONE -> {}

        CurrentModal.LOWBATTERY -> LowBatteryModal(
            onDismiss = {
                lowBattery = false
                verificarModal()

            }
        )

        CurrentModal.NOINTERNET -> {
        }

        CurrentModal.ECONOMY -> {

        }
    }

    when(currentScreen){

        CurrentScreen.QUIZMYBRAIN -> QuizMyBrainScreen(
            questoes = questoes,
            onBack = {currentScreen = CurrentScreen.HOME}
        )

        CurrentScreen.HOME -> {
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
}
