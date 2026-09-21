package com.example.mod_a1treino.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mod_a1treino.ui.models.DadosDto
import com.example.mod_a1treino.ui.models.ListDadosDto
import com.example.mod_a1treino.ui.models.PerguntaMeVf
import com.example.mod_a1treino.ui.models.PerguntaRel
import com.example.mod_a1treino.ui.repository.DataRepository
import kotlinx.serialization.json.decodeFromJsonElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizMyBrainScreen(onHome: () -> Unit) {

    val context = LocalContext.current

    var questoes by remember { mutableStateOf<List<DadosDto>>(emptyList()) }
    var questaoAtual by remember { mutableIntStateOf(0) }


    LaunchedEffect(Unit) {
        questoes = DataRepository.loadJson(context)
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
                                questaoAtual++
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
                        VerdadeiroFalsoScreen(questao = questao)
                    }

                    "ME" -> {
                        MultiplaEscolhaScreen(questao = questao)
                    }

                    "REL" -> {
                        RelacionalScreen(questao = questao)
                    }
                }
            }
        }
    }
}