package com.example.simuladoa2refeito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.simuladoa2refeito.ui.enums.CurrentScreen
import com.example.simuladoa2refeito.ui.models.EscolaData
import com.example.simuladoa2refeito.ui.repository.DataRepository
import com.example.simuladoa2refeito.ui.screens.EscolaScreenDetail
import com.example.simuladoa2refeito.ui.screens.HomeScreen
import com.example.simuladoa2refeito.ui.screens.ListSchoolScreen
import com.example.simuladoa2refeito.ui.screens.LoginScreen
import com.example.simuladoa2refeito.ui.screens.MapaScreen
import com.example.simuladoa2refeito.ui.screens.SplashScreen
import com.example.simuladoa2refeito.ui.theme.SimuladoA2RefeitoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimuladoA2RefeitoTheme {
                AppRoot()

            }
        }
    }
}

@Composable
fun AppRoot(){
    val escolas = remember { mutableStateListOf<EscolaData>() }
    var currentScreen by remember { mutableStateOf(CurrentScreen.SPLASH) }
    var selectedSchool by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current
    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        escolas.addAll(DataRepository.getSchoolList().escolas)
    }

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

        CurrentScreen.LIST_ESCOLA -> ListSchoolScreen(
            onClick = {
                selectedSchool = it
                currentScreen = CurrentScreen.DETAIL_ESCOLA
            },
            onSchoolList = { currentScreen = CurrentScreen.LIST_ESCOLA },
            onLogout = { currentScreen = CurrentScreen.LOGIN },
            onMap = {currentScreen = CurrentScreen.MAP},
            currentScreen = currentScreen,
            context = context,
        )

        CurrentScreen.DETAIL_ESCOLA -> EscolaScreenDetail(
            escolas = escolas,
            id = selectedSchool!!,
            onBack = { currentScreen = CurrentScreen.LIST_ESCOLA },
            onLogout = { currentScreen = CurrentScreen.LOGIN },
            context = context,
        )

        CurrentScreen.MAP -> MapaScreen(
            onSchoolList = { currentScreen = CurrentScreen.LIST_ESCOLA },
            currentScreen = currentScreen,
            onMap = { currentScreen = CurrentScreen.MAP },
            onLogout = { currentScreen = CurrentScreen.LOGIN }
        )

    }
}