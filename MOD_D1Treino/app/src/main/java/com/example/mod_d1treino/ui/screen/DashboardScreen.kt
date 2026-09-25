package com.example.mod_d1treino.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mod_d1treino.ui.components.Modal
import com.example.mod_d1treino.ui.components.NoGridModeCursos
import com.example.mod_d1treino.ui.models.Curso
import com.example.mod_d1treino.ui.preferences.CoursePreferences
import com.example.mod_d1treino.ui.repository.DataRepository
import kotlin.collections.emptyList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onHome: () -> Unit,
    onAdd: () -> Unit,
    onTeachers: () -> Unit,
    onRelatorio: () -> Unit,
    preferences: CoursePreferences
) {


    val context = LocalContext.current
    val repository = DataRepository(context = context)

    val cursos by preferences.cursos.collectAsStateWithLifecycle(emptyList())
    var cursosJson by remember { mutableStateOf<List<Curso>>(emptyList()) }

    LaunchedEffect(Unit) {
        cursosJson = repository.loadCursos()
    }

    var isGridMode by remember { mutableStateOf(false) }
    var busca by remember { mutableStateOf("") }
    var showLongModal by remember { mutableStateOf(false) }
    var showShortModal by remember { mutableStateOf(false) }
    var cursoSelected by remember { mutableStateOf<Curso?>(null) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DashBoard") },
                navigationIcon = { IconButton(onClick = onHome) { Icon(Icons.Default.Home,contentDescription = null) }
                },
                actions = {
                    IconButton(onClick = {isGridMode = true}, modifier = Modifier.testTag("gridTrue")) {
                        Icon(Icons.Default.GridView,contentDescription = null)
                    }
                    IconButton(onClick = {isGridMode = false}, modifier = Modifier.testTag("gridFalse")) {
                        Icon(Icons.Default.FormatListBulleted,contentDescription = null)
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Book,contentDescription = null)
                    }
                    IconButton(onClick = onTeachers, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.AccountCircle,contentDescription = null)
                    }
                    IconButton(onClick = onRelatorio, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Note,contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            IconButton(onClick = onAdd,modifier = Modifier.testTag("btnAdd")) {
                Icon(Icons.Default.Add,contentDescription = null)
            }
        },


        ) { innerPad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPad),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (showLongModal && cursoSelected != null)
                Modal(curso = cursoSelected!!, onDismiss = { showLongModal = false ; showShortModal = false; cursoSelected = null})

            if (showShortModal && cursoSelected != null)
                Modal(curso = cursoSelected!!, onDismiss = { showShortModal = false ;showLongModal = false; cursoSelected = null})



            OutlinedTextField(
                modifier = Modifier.testTag("buscaDash"),
                value = busca,
                onValueChange = {novoValor->
                    if (novoValor.all{it.isLetter() || it == '-'|| it.isWhitespace()}  )
                        busca = novoValor
                    else
                        Toast.makeText(context,"Caracteres inválidos não são aceitos", Toast.LENGTH_SHORT).show()
                },
                trailingIcon = {Icon(Icons.Default.Search,contentDescription = null)}
            )
            var cursosFiltrados = cursos.filter { it.nomeBreve.contains(busca, ignoreCase = true) || it.nomeCompleto.contains(busca, ignoreCase = true) }
            cursosFiltrados += cursosJson.filter { it.nomeBreve.contains(busca, ignoreCase = true) || it.nomeCompleto.contains(busca, ignoreCase = true)}
            if (!isGridMode) {
                if (cursosFiltrados.isEmpty()){
                    Text("Nenhum curso encontrado")
                }
                else
                    cursosFiltrados.forEach { curso ->
                        NoGridModeCursos(modifier = Modifier.testTag("curso_${curso.id}"),curso, onTap = { showShortModal = true; cursoSelected = curso }, onLongPress = {showLongModal = true; cursoSelected = curso})

                    }
            } else {
                if (cursosFiltrados.isEmpty()){
                    Text("Nenhum curso encontrado")
                }
                else
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2)
                    ) {
                        items(cursosFiltrados){curso->
                            NoGridModeCursos(modifier = Modifier.testTag("curso_${curso.id}"),curso, onTap = { showShortModal = true; cursoSelected = curso }, onLongPress = {showLongModal = true; cursoSelected = curso})

                        }
                    }
            }
        }
    }
}
