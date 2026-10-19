package com.example.simuladod1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.simuladod1.ui.models.Categoria
import com.example.simuladod1.ui.models.CurrentScreen
import com.example.simuladod1.ui.models.Curso
import com.example.simuladod1.ui.models.JsonData
import com.example.simuladod1.ui.models.Professor
import com.example.simuladod1.ui.preferences.TeacherPreference
import com.example.simuladod1.ui.repository.DataRepository
import com.example.simuladod1.ui.screens.CadastroCursoScreen
import com.example.simuladod1.ui.screens.CadastroProfessorScreen
import com.example.simuladod1.ui.screens.DashboardScreen
import com.example.simuladod1.ui.screens.ProfessorListScreen
import com.example.simuladod1.ui.screens.RelatoriosScreen
import com.example.simuladod1.ui.theme.SimuladoD1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimuladoD1Theme {
                AppRoot()
            }
        }
    }
}
@Composable
fun AppRoot(){
    val context = LocalContext.current

    val preferences = remember {
        TeacherPreference(context)
    }
    val professores by preferences.professores.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    val cursos = remember { mutableStateListOf< Curso>() }
    val categorias = remember { mutableStateListOf<Categoria>() }


    var currentScreen by remember() { mutableStateOf(CurrentScreen.DASHBOARD) }

    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {

        isLoading = true
        val dados = DataRepository.loadJson(context)

        dados.professores_id.forEach { professor ->
            preferences.salvarProfessor(professor)
        }

        cursos.addAll(dados.cursos)
        categorias.addAll(dados.categorias)

        isLoading = false
    }

    if (isLoading){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ){
            CircularProgressIndicator()
        }
        return
    }

    when(currentScreen){

        CurrentScreen.DASHBOARD -> DashboardScreen(

            cursos = cursos,
            onDashboard = { currentScreen = CurrentScreen.DASHBOARD },
            onAdd = { currentScreen = CurrentScreen.CAD_CURSO },
            currentScreen = currentScreen,
            onListProfessor = { currentScreen = CurrentScreen.LIST_PROFESSOR },
            onRelatorio = { currentScreen = CurrentScreen.RELATORIO },
        )

        CurrentScreen.CAD_CURSO -> CadastroCursoScreen(
            categorias = categorias,
            professores = professores,
            onDashboard = {currentScreen = CurrentScreen.DASHBOARD}
        )
        CurrentScreen.CAD_PROFESSOR -> CadastroProfessorScreen(
            context = context,

            onDashboard = { currentScreen = CurrentScreen.DASHBOARD },
            preference = preferences,
            professores = professores
        )


        CurrentScreen.LIST_PROFESSOR -> ProfessorListScreen(
            currentScreen = currentScreen,
            onAdd = {currentScreen = CurrentScreen.CAD_PROFESSOR},
            onDashboard = { currentScreen = CurrentScreen.DASHBOARD },
            onRelatorio = { currentScreen = CurrentScreen.RELATORIO },
            professoresJson = professores
        )

        CurrentScreen.RELATORIO -> RelatoriosScreen(
            cursos = cursos,
            professores = professores,
            onHomeClick = { currentScreen = CurrentScreen.DASHBOARD },
            onCursosClick = {currentScreen = CurrentScreen.DASHBOARD},
            onProfessoresClick = { currentScreen = CurrentScreen.LIST_PROFESSOR }
        )
    }
}
