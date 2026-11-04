package com.example.a1ateondeder.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.a1ateondeder.ui.theme.models.Pergunta
import com.example.a1ateondeder.ui.theme.repository.DataRepository
import com.example.a1ateondeder.ui.theme.screens.quizmybrain.MultiplaEscolha
import com.example.a1ateondeder.ui.theme.screens.quizmybrain.Relacione
import com.example.a1ateondeder.ui.theme.screens.quizmybrain.VerdadeiroFalso
import kotlinx.serialization.json.decodeFromJsonElement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizMyBrainScreen(
    questoes: List<Pergunta>
) {

    var index by remember { mutableIntStateOf(0) }
    var questaoSelecionada by remember { mutableStateOf<Int?>(null) }
    val questao = questoes[index]
    var mostrarResposta by remember { mutableStateOf(false) }
    var mostrarScore by remember { mutableStateOf(false) }
    var score by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Quiz My Brain") }
            )
        },
        bottomBar = {
            Row(Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
            ) {
                Button(
                    onClick = {

                    }
                ) {
                    Text("Encerrar")
                }
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = {
                        if (!mostrarResposta){
                            val resposta = DataRepository.jsonInstance.decodeFromJsonElement<Int>(questao.resposta)

                            if (questaoSelecionada == resposta) score += questao.peso
                            mostrarResposta = true

                        } else if (index < questoes.lastIndex){
                            index++
                            questaoSelecionada = null
                            mostrarResposta = false
                        } else {
                            mostrarScore = true
                        }
                    }
                ) {
                    Text("Próximo")
                }
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = paddingValues)
        ) {
            when(questao.tipo) {
                "VF" -> VerdadeiroFalso()

                "ME" -> MultiplaEscolha(

                    mostrarResposta = mostrarResposta,
                    questao = questao,
                    onSelect = { questaoSelecionada = it },
                    alternativaSelecionada = questaoSelecionada
                )

                "REL" -> Relacione()
            }
        }
    }
}