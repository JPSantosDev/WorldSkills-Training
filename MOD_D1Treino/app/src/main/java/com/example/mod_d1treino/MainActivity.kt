package com.example.mod_d1treino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.mod_d1treino.enums.CurrentScreen
import com.example.mod_d1treino.ui.preferences.CoursePreferences
import com.example.mod_d1treino.ui.repository.DataRepository
import com.example.mod_d1treino.ui.screen.CadastroCourseScreen
import com.example.mod_d1treino.ui.screen.DashboardScreen
import com.example.mod_d1treino.ui.theme.MOD_D1TreinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MOD_D1TreinoTheme {
                AppRoot()

            }
        }
    }
}

@Composable
fun AppRoot(){

    val context = LocalContext.current
    val preferences = CoursePreferences(
        context = context
    )
    var currentScreen by remember { mutableStateOf(CurrentScreen.DASHBOARD) }
    val repository = DataRepository(context = LocalContext.current)

    when(currentScreen){
        CurrentScreen.DASHBOARD -> DashboardScreen(
            onHome = {},
            onAdd = { currentScreen = CurrentScreen.CADASTRO_CURSOS },
            onTeachers = { currentScreen = CurrentScreen.LIST_PROFESSORES },
            onRelatorio = { currentScreen = CurrentScreen.RELATORIOS },
            preferences = preferences
        )

        CurrentScreen.CADASTRO_CURSOS -> CadastroCourseScreen(
            onHome = { currentScreen = CurrentScreen.DASHBOARD },
            repository = repository,
            preferences = preferences,
            onDashboard = { currentScreen = CurrentScreen.DASHBOARD }
        )

        CurrentScreen.LIST_PROFESSORES -> TODO()

        CurrentScreen.CADASTRO_PROFESSORES -> TODO()

        CurrentScreen.RELATORIOS -> TODO()
    }
}
