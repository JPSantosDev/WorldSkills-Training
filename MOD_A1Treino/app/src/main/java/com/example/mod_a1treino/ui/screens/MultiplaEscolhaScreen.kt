package com.example.mod_a1treino.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mod_a1treino.ui.models.DadosDto
import com.example.mod_a1treino.ui.repository.DataRepository
import kotlinx.serialization.json.decodeFromJsonElement

@Composable
fun MultiplaEscolhaScreen(
    questao: DadosDto,
    mostrarResultado: Boolean,
    alternativaSelecionada: Int?,
    onAlternativaSelecionada: (Int) -> Unit
) {

    val scroll = rememberScrollState()

    val resposta: Int =
        DataRepository.jsonInstance.decodeFromJsonElement(questao.resposta)

    val alternativas: List<String> =
        DataRepository.jsonInstance.decodeFromJsonElement(questao.alternativas)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scroll)
    ) {

        Text(text = questao.enunciado)

        alternativas.forEachIndexed { index, alternativa ->

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

                Text("${'a' + index}) $alternativa")
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