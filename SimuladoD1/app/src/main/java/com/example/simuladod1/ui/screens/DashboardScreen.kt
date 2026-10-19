package com.example.simuladod1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.simuladod1.ui.models.CurrentScreen
import com.example.simuladod1.ui.models.Curso

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onDashboard: () -> Unit,
    onAdd: () -> Unit,
    cursos: List<Curso>,
    currentScreen: CurrentScreen,
    onListProfessor: () -> Unit,
    onRelatorio: () -> Unit
) {

    var busca by remember { mutableStateOf("") }
    var isGridMode by remember { mutableStateOf(false) }


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
            NavigationBar(
            ) {
                NavigationBarItem(
                    selected = currentScreen == CurrentScreen.DASHBOARD,
                    onClick = {  },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = null
                        )
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == CurrentScreen.LIST_PROFESSOR,
                    onClick = { onListProfessor() },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == CurrentScreen.RELATORIO,
                    onClick = { onRelatorio() },
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
                    Text("Dashboard")
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
                actions = {
                    IconButton(
                        onClick = {
                            isGridMode = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            isGridMode = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatListBulleted,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()

        ) {
            OutlinedTextField(
                value = busca,
                onValueChange = {busca = it},
                trailingIcon = {Icon(Icons.Default.Search,contentDescription = null)},
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (isGridMode){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cursos){curso->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ){
                                CircularProgressIndicator(color = Color.Gray, progress = 1f, modifier = Modifier.size(80.dp))
                                CircularProgressIndicator(progress = (curso.porcentagem).toFloat(),color = Color.Red,modifier = Modifier.size(80.dp))
                                Text((curso.porcentagem*100).toString()+"%")
                            }
                            Text(curso.nomeCompleto)
                        }
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(cursos){curso->
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ){
                                CircularProgressIndicator(color = Color.Gray, progress = 1f, modifier = Modifier.size(80.dp))
                                CircularProgressIndicator(progress = (curso.porcentagem).toFloat(),color = Color.Red,modifier = Modifier.size(80.dp))
                                Text((curso.porcentagem*100).toString()+"%")
                            }
                            Text(curso.nomeCompleto)
                        }
                    }
                }
            }
        }
    }
}