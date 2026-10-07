package com.example.mod_d1treino.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.mod_d1treino.ui.components.ProfessorCard
import com.example.mod_d1treino.ui.models.Professor
import com.example.mod_d1treino.ui.repository.DataRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessoresListScreen(
    onHome: () -> Unit,
    onRelatorio: () -> Unit,
    onAdd: () -> Unit,
    onCursos: () -> Unit,
    repository: DataRepository,
    professores: MutableList<Professor>
) {


    var busca by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val carregados = repository.loadProfessores()
        professores.addAll(carregados)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Professores") },
                navigationIcon = { IconButton(onClick = onHome) { Icon(Icons.Default.Home,contentDescription = null) }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onCursos, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Book,contentDescription = null)
                    }
                    IconButton(onClick = {}, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.AccountCircle,contentDescription = null, tint = Color.Gray)
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
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPad),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = busca,
                onValueChange = {busca = it},
                trailingIcon = {
                    Icon(Icons.Default.Search,contentDescription = null)
                }
            )

            val professoresFiltrados = professores.filter {it.nome.contains(busca, ignoreCase = true)}

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(professoresFiltrados){ professor->
                    ProfessorCard(
                        professor = professor,
                        onDelete = {professores.remove(professor)}
                    )
                }
            }
        }
    }
}