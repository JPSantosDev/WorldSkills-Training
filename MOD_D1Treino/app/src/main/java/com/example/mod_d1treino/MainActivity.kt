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
import com.example.mod_d1treino.enums.CurrentScreen
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
    var currentScreen by remember { mutableStateOf(CurrentScreen.DASHBOARD) }

    when(currentScreen){
        CurrentScreen.DASHBOARD -> DashboardScreen(
            onHome = {  },
            onAdd = {  },
            onTeachers = {  },
            onRelatorio = {  }
        )

        CurrentScreen.CADASTRO_CURSOS -> TODO()
        CurrentScreen.LIST_PROFESSORES -> TODO()
        CurrentScreen.CADASTRO_PROFESSORES -> TODO()
        CurrentScreen.RELATORIOS -> TODO()
    }

}
