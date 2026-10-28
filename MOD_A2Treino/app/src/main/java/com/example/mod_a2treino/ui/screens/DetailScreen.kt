package com.example.mod_a2treino.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mod_a2treino.ui.components.ReactionSlider
import com.example.mod_a2treino.ui.models.School

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack:()->Unit,
    id: Int,
    escolas: List<School>,
){
    val escola = escolas.find {it.id == id}!!


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes Escola") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(escola.nome)
            ReactionSlider(escola.avaliacao.toFloat())


        }
    }
}