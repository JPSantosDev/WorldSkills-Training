package com.example.mod_a2treino.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.mod_a2treino.ui.components.EscolaCard
import com.example.mod_a2treino.ui.models.School
import com.example.mod_a2treino.ui.repository.ApiClient
import com.example.mod_a2treino.ui.repository.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onClick: (Int) -> Unit,
    escolas: List<School>,
    context: Context,
    onLogout: () -> Unit,

) {
    LaunchedEffect(Unit) {
        try {
            ApiClient.validateToken(TokenManager.token ?: "")
        } catch (e: Exception) {
            Toast.makeText(context, "Token inválido ou expirado", Toast.LENGTH_SHORT).show()
            onLogout()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Escolas") }
            )
        }
    ) { pad->
        Text("Avaliação")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            items(escolas){ escola->
                EscolaCard(
                    escola = escola,
                    onClickSchool = { onClick(it)}
                )
            }
        }
    }
}