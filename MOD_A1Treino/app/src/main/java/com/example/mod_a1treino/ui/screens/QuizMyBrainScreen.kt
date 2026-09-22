package com.example.mod_a1treino.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mod_a1treino.ui.models.DadosDto
import com.example.mod_a1treino.ui.repository.DataRepository
import kotlinx.serialization.json.decodeFromJsonElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizMyBrainScreen(onHome: () -> Unit, ) {

    val context = LocalContext.current
    val activity = context as Activity

    var requestedOrientation by rememberSaveable() { mutableIntStateOf(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) }
    var mostrarResultado by rememberSaveable() { mutableStateOf(false) }

    var score by rememberSaveable() { mutableIntStateOf(0) }

    var questoes by remember() { mutableStateOf<List<DadosDto>>(emptyList()) }
    var questaoAtual by rememberSaveable() { mutableIntStateOf(0) }
    var alternativaSelecionada by remember() { mutableStateOf<Int?>(null) }


    LaunchedEffect(Unit){
        questoes = DataRepository.loadJson(context)
    }

    LaunchedEffect(questaoAtual) {
        activity.requestedOrientation = requestedOrientation
    }

    fun onClickProximo(){
        if (!mostrarResultado) {
            val questao = questoes[questaoAtual]

            val resposta: Int = DataRepository.jsonInstance.decodeFromJsonElement(questao.resposta)
            if (resposta == alternativaSelecionada) {
                score+=questao.peso
            }
            mostrarResultado = true

        } else{
            questaoAtual++
            mostrarResultado = false
            alternativaSelecionada = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz My Brain") },
                navigationIcon = {
                    IconButton(
                        onClick = onHome
                    ) {
                        Icon(Icons.Default.ArrowBack,contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row(Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {}
                        ) {
                            Text("Encerrar")
                        }
                        Spacer(Modifier.weight(1f))
                        Button(
                            onClick = {
                                onClickProximo()
                            }
                        ) {
                            Text("Próximo")
                        }
                    }
                }
            )
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
        ) {
            if (questoes.isNotEmpty()) {

                val questao = questoes[questaoAtual]
                when (questao.tipo) {

                    "VF" -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                        VerdadeiroFalsoScreen(questao)
                    }

                    "ME" -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                        MultiplaEscolhaScreen(
                            questao,
                            mostrarResultado,
                            alternativaSelecionada,
                            onAlternativaSelecionada = { alternativaSelecionada=it }
                        )
                    }

                    "REL" -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        RelacionalScreen(questao)
                    }
                }
            }
        }
    }
}