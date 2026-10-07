package com.example.mod_ctreino.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mod_ctreino.ui.components.CardPin
import com.example.mod_ctreino.ui.components.Carrousel
import com.example.mod_ctreino.ui.model.DadosDto
import com.example.mod_ctreino.ui.repository.DataRepository

@Composable
fun HomeScreen(
    onHome: () -> Unit,
    onExplore: () -> Unit,
    onExercises: () -> Unit,
    onArticles: () -> Unit,
    onPerfil: () -> Unit,
    repository: DataRepository
) {
    var artigos by remember { mutableStateOf<List<DadosDto>>(emptyList()) }
    val scroll = rememberScrollState()

    LaunchedEffect(Unit) {
        artigos = repository.loadJson()
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onHome, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = onExplore, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Explore, contentDescription = "Explorar")
                    }
                    IconButton(onClick = onExercises, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Book, contentDescription = "Exercícios")
                    }
                    IconButton(onClick = onArticles, modifier = Modifier.weight(1f)) {
                        Icon(Icons.AutoMirrored.Filled.Article, contentDescription = "Artigos")
                    }
                    IconButton(onClick = onPerfil, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.AccountBox, contentDescription = "Perfil")
                    }
                }
            )
        }
    ) { pad ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(16.dp)
                .verticalScroll(scroll)
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Olá, Estudante!",
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Default.Notifications, contentDescription = "Notificações")
                }
                Spacer(Modifier.height(16.dp))
                Carrousel(artigos)
            }
            Spacer(Modifier.height(16.dp))
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
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPre() {
    val repository = DataRepository(LocalContext.current)
    HomeScreen(
        onHome = {},
        onExplore = {},
        onExercises = {},
        onPerfil = {},
        onArticles = {},
        repository = repository
    )
}
