package com.example.mod_d1treino

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.mod_d1treino.enums.CurrentScreen
import com.example.mod_d1treino.ui.models.Categoria
import com.example.mod_d1treino.ui.models.Curso
import com.example.mod_d1treino.ui.models.Professor
import com.example.mod_d1treino.ui.preferences.CoursePreferences
import com.example.mod_d1treino.ui.repository.DataRepository
import com.example.mod_d1treino.ui.screen.CadastroCourseScreen
import com.example.mod_d1treino.ui.screen.CadastroProfessor
import com.example.mod_d1treino.ui.screen.DashboardScreen
import com.example.mod_d1treino.ui.screen.ProfessoresListScreen
import com.example.mod_d1treino.ui.screen.RelatorioScreen
import com.example.mod_d1treino.ui.theme.MOD_D1TreinoTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppRoot(){

    var professores = remember { mutableStateListOf<Professor>() }
    var categorias = remember { mutableStateListOf<Categoria>() }
    var cursos = remember { mutableStateListOf<Curso>() }

    val context = LocalContext.current
    val preferences = CoursePreferences(
        context = context
    )
    var currentScreen by remember { mutableStateOf(CurrentScreen.DASHBOARD) }
    val repository = DataRepository(context = LocalContext.current)

    LaunchedEffect(Unit) {
        val professoresCarregados = repository.loadProfessores()
        val cursosCarregados = repository.loadCursos()
        val categoriasCarregadas = repository.loadCategorias()

        professores.addAll(professoresCarregados)
        cursos.addAll(cursosCarregados)
        categorias.addAll(categoriasCarregadas)

    }
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
            preferences = preferences,
            onDashboard = { currentScreen = CurrentScreen.DASHBOARD },
            repository = repository
        )

        CurrentScreen.LIST_PROFESSORES -> ProfessoresListScreen(
            onHome = { currentScreen = CurrentScreen.DASHBOARD },
            onRelatorio = { currentScreen = CurrentScreen.RELATORIOS },
            onAdd = { currentScreen = CurrentScreen.CADASTRO_PROFESSORES },
            onCursos = { currentScreen = CurrentScreen.DASHBOARD },
            repository = repository,
            professores = professores
        )

        CurrentScreen.CADASTRO_PROFESSORES -> CadastroProfessor(
            onHome = { currentScreen = CurrentScreen.DASHBOARD },
            onDashboard = { currentScreen = CurrentScreen.DASHBOARD },
            professores = professores,
            context = context,
        )

        CurrentScreen.RELATORIOS -> RelatorioScreen(
            professores = professores,
            cursos = cursos,
            onHome = { currentScreen = CurrentScreen.DASHBOARD },
            onGerarRelatorio = { },
            onCursos = { currentScreen = CurrentScreen.DASHBOARD },
            onTeachers = { currentScreen = CurrentScreen.LIST_PROFESSORES },
            onSalvar = {  },
        ) 
    }
}
