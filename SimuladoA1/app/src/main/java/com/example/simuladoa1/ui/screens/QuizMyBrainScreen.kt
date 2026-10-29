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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.simuladoa1.ui.device.TiltObserver
import com.example.simuladoa1.ui.device.selecoesPorInclinacao
import com.example.simuladoa1.ui.models.DadosCurso
import com.example.simuladoa1.ui.repository.DataRepository
import com.example.simuladoa1.ui.screens.quizmybrain.MultiplaEscolha
import com.example.simuladoa1.ui.screens.quizmybrain.Relacional
import com.example.simuladoa1.ui.screens.quizmybrain.VerdadeiroFalso
import kotlinx.serialization.json.decodeFromJsonElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizMyBrainScreen(onHome: () -> Unit, ) {

    val context = LocalContext.current
    val activity = context as Activity

    var requestedOrientation by remember { mutableIntStateOf(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) }
    var mostrarResultado by rememberSaveable() { mutableStateOf(false) }

    var score by rememberSaveable() { mutableIntStateOf(0) }

    var questoes by rememberSaveable() { mutableStateOf<List<DadosCurso>>(emptyList()) }
    var questaoAtual by rememberSaveable() { mutableIntStateOf(0) }
    var alternativaSelecionada by remember() { mutableStateOf<Int?>(null) }
    var alternativaSelecionadaRel by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var encerrado by remember { mutableStateOf(false) }
    var relacoes by rememberSaveable(questaoAtual) {
        mutableStateOf<Map<Int, Int>>(emptyMap())
    }

    LaunchedEffect(Unit){
        questoes = DataRepository.loadJson(context)
    }

    LaunchedEffect(questaoAtual) {
        activity.requestedOrientation = requestedOrientation
    }

    fun onClickProximo(){
        if (!mostrarResultado) {
            val questao = questoes[questaoAtual]

            if (questao.tipo == "VF" || questao.tipo == "ME"){

                val resposta: Int = DataRepository.jsonInstance.decodeFromJsonElement(questao.resposta)
                if (resposta == alternativaSelecionada) {
                    score+=questao.peso
                }

            } else {
                val resposta: List<List<Int>> = DataRepository.jsonInstance.decodeFromJsonElement(questao.resposta)
                if (resposta == alternativaSelecionadaRel){
                    score+=questao.peso
                }
            }

            mostrarResultado = true

        } else {
            questaoAtual++
            mostrarResultado = false
            alternativaSelecionada = null
            alternativaSelecionadaRel = null
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
                            onClick = {
                                encerrado = true
                            }
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
                        VerdadeiroFalso(
                            questao = questao,
                            alternativaSelecionada = alternativaSelecionada,
                            onAlternativaSelecionada = { alternativaSelecionada = it },
                            mostrarResultado = mostrarResultado,
                        )
                    }

                    "ME" -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                        MultiplaEscolha(
                            questao = questao,
                            mostrarResultado = mostrarResultado,
                            alternativaSelecionada = alternativaSelecionada,
                            onAlternativaSelecionada = { alternativaSelecionada = it }
                        )
                    }

                    "REL" -> {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        Relacional(
                            mostrarResultado = mostrarResultado ,
                            questao = questao,
                            relacoes = relacoes,
                            onRelacionar = { termo, conceito ->
                                relacoes = relacoes.filterValues { it != conceito } + (termo to conceito)
                            },
                        )
                    }
                }
            }
            if (encerrado){
                Column(Modifier.fillMaxWidth()) {
                    Text(score.toString())
                }
            }
        }
    }
}



