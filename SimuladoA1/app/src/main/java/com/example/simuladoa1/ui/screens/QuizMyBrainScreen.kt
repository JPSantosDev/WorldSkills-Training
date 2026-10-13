package com.example.simuladoa1.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.simuladoa1.ui.models.DadosCurso
import com.example.simuladoa1.ui.repository.DataRepository
import kotlinx.serialization.json.decodeFromJsonElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizMyBrainScreen(onHome: () -> Unit, ) {

    val context = LocalContext.current
    val activity = context as Activity

    var requestedOrientation by rememberSaveable() { mutableIntStateOf(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) }
    var mostrarResultado by rememberSaveable() { mutableStateOf(false) }

    var score by rememberSaveable() { mutableIntStateOf(0) }

    var questoes by remember() { mutableStateOf<List<DadosCurso>>(emptyList()) }
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

                        VerdadeiroFalso(questao)
                    }

                    "ME" -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                        MultiplaEscolha(
                            questao,
                            mostrarResultado,
                            alternativaSelecionada,
                            onAlternativaSelecionada = { alternativaSelecionada = it }
                        )
                    }

                    "REL" -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        Relacional(questao)
                    }
                }
            }
        }
    }
}

// As telas do QuizMyBrain
@Composable
fun MultiplaEscolha(
    questao: DadosCurso,
    mostrarResultado: Boolean,
    alternativaSelecionada: Int?,
    onAlternativaSelecionada: (Int) -> Unit
) {

    val scroll = rememberScrollState()

    val resposta: Int = DataRepository.jsonInstance.decodeFromJsonElement(questao.resposta)

    val opcoes: List<String> = DataRepository.jsonInstance.decodeFromJsonElement(questao.alternativas)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scroll)
    ) {

        Text(text = questao.enunciado)

        opcoes.forEachIndexed { index, opcao ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = alternativaSelecionada == index,
                    onClick = {
                        if (!mostrarResultado) {
                            onAlternativaSelecionada(index)
                        }
                    }
                )

                Text("${'a' + index}) $opcao")
            }
        }

        if (mostrarResultado) {

            if (alternativaSelecionada == resposta) {
                Text(
                    text = "Resposta correta! +${questao.peso} pontos"
                )
            } else {
                Text(
                    text = "Resposta incorreta!"
                )
            }
        }
    }
}

@Composable
fun Relacional(questao: DadosCurso){

}
@Composable
fun VerdadeiroFalso(questao: DadosCurso){

}