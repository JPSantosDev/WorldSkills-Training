package com.example.a1ateondeder.ui.theme.screens.quizmybrain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.a1ateondeder.ui.theme.models.Pergunta
import com.example.a1ateondeder.ui.theme.repository.DataRepository
import kotlinx.serialization.json.decodeFromJsonElement


@Composable
fun MultiplaEscolha(
    questao: Pergunta,
    mostrarResposta: Boolean,
    alternativaSelecionada: Int?,
    onSelect: (Int) -> Unit
){

    val alternativas = remember(questao){
        DataRepository.jsonInstance.decodeFromJsonElement<List<String>>(questao.alternativas)
    }

    val resposta = remember(questao){
        DataRepository.jsonInstance.decodeFromJsonElement<Int>(questao.resposta)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(questao.enunciado)

        alternativas.forEachIndexed { index, string ->


            val fundo = when {
                mostrarResposta && index == resposta -> Color.Green
                index == alternativaSelecionada -> MaterialTheme.colorScheme.primaryContainer
                else -> Color.Transparent

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(fundo),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = alternativaSelecionada == index,
                    onClick = {
                        if (!mostrarResposta) onSelect(index)
                    }
                )
                Text(string)
            }
        }
    }
}