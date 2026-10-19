package com.example.simuladod1.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.simuladod1.ui.models.CurrentScreen
import com.example.simuladod1.ui.models.Professor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorListScreen(
    currentScreen: CurrentScreen,
    onAdd: () -> Unit,
    onDashboard: () -> Unit,
    onRelatorio: () -> Unit,
    professoresJson: List<Professor>
){



    var busca by remember { mutableStateOf("") }

    var professorLongPress by remember { mutableStateOf<Int?>(null) }

    val professores = remember {
        mutableStateListOf<Professor>().apply {
            addAll(professoresJson)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = currentScreen == CurrentScreen.DASHBOARD,
                    onClick = {onDashboard()},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = null
                        )
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == CurrentScreen.LIST_PROFESSOR,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == CurrentScreen.RELATORIO,
                    onClick = { onRelatorio()},
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Note,
                            contentDescription = null
                        )
                    }
                )

            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Professores")
                },
                navigationIcon = {
                    IconButton(
                        onClick ={
                            onDashboard()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home ,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) {pad->
        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                trailingIcon = {Icon(Icons.Default.Search,contentDescription = null)},
                value = busca,
                onValueChange = {busca = it}
            )

            LazyColumn {
                items(professores){professor->
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .border(1.dp,Color.Gray)
                            .pointerInput(professor.id) {
                                detectTapGestures(
                                    onLongPress = {
                                        professorLongPress = professor.id
                                    }
                                )
                            }
                    ) {
                        Text(professor.nome)
                        Row {
                            Text(professor.descricao)
                            if (professorLongPress == professor.id){
                                IconButton(
                                    onClick = { professores.remove(professor) }
                                ) {
                                    Icon(Icons.Default.Delete,contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}