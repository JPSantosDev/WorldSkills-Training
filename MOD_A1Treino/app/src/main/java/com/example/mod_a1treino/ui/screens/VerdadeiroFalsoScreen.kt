package com.example.mod_a1treino.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mod_a1treino.ui.models.DadosDto
import com.example.mod_a1treino.ui.models.PerguntaMeVf

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun VerdadeiroFalsoScreen(
    questao: DadosDto,
){
    val context = LocalContext.current


    Column(Modifier.fillMaxSize()) {
        Text("Verdadeiro falso")
    }
}