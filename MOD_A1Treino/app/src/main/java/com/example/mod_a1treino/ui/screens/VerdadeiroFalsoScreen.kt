package com.example.mod_a1treino.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mod_a1treino.ui.models.DadosDto
import com.example.mod_a1treino.ui.models.PerguntaMeVf

@Composable
fun VerdadeiroFalsoScreen(
    questao: DadosDto,
){
    Column(Modifier.fillMaxSize()) {
        Text("Verdadeiro falso")
    }
}