package com.example.mod_ctreino.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.scrollableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mod_ctreino.ui.components.CardPin
import com.example.mod_ctreino.ui.model.DadosDto
import com.example.mod_ctreino.ui.repository.DataRepository
import com.example.mod_ctreino.ui.components.CardPin
import com.example.mod_ctreino.ui.components.Carrousel

@Composable
fun HomeScreen(
    onHome: () -> Unit,
    onExplore: () -> Unit,
    onExercises: () -> Unit,
    onArticles: () -> Unit,
    onPerfil: () -> Unit,
    repository: DataRepository
) {

    var artigos by remember() { mutableStateOf<List<DadosDto>>(emptyList()) }
    val scroll = rememberScrollState()

    LaunchedEffect(Unit) {
       artigos = repository.loadJson()
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onHome, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Home,contentDescription = null) }
                    IconButton(onClick = onExplore, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Explore,contentDescription = null) }
                    IconButton(onClick = onExercises, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Book,contentDescription = null) }
                    IconButton(onClick = onArticles, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Article,contentDescription = null) }
                    IconButton(onClick = onPerfil, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.AccountBox,contentDescription = null) }
                }
            )
        }
    ) { pad ->


        Column(Modifier
            .fillMaxSize()
            .padding(pad)
            .scrollableArea(scroll, orientation = Orientation.Vertical, enabled = true)
        ) {
            Column(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row() {
                    Text("Olá, Estudante!",
                        modifier = Modifier
                            .fillMaxWidth(0.93f))
                    Icon(Icons.Default.Notifications, contentDescription = null)
                }
                Carrousel(artigos)
            }
            Column(Modifier.fillMaxWidth()) {
                Text("Artigos")
                Spacer(Modifier.height(8.dp))
                artigos.forEach {
                    CardPin(
                        id = it.id,
                        titulo = it.titulo,
                        descricao = it.descricao,
                        data = it.data
                    )
                    Spacer(Modifier.height(4.dp))
                }

            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPre(){
    val repository = DataRepository(LocalContext.current)
    HomeScreen(
        onHome = { },
        onExplore = { },
        onExercises = { },
        onPerfil = {},
        onArticles = {  },
        repository =repository
    )
}