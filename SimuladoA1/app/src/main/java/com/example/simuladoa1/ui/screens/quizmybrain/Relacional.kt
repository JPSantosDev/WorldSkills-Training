package com.example.simuladoa1.ui.screens.quizmybrain

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simuladoa1.ui.models.DadosCurso
import com.example.simuladoa1.ui.repository.DataRepository
import kotlinx.serialization.json.decodeFromJsonElement

@Composable
fun Relacional(
    relacoes: Map<Int, Int>,
    questao: DadosCurso,
    onRelacionar: (termo:Int, conceito:Int) -> Unit,
    mostrarResultado: Boolean
){

    val alternativas: List<List<String>> = DataRepository.jsonInstance.decodeFromJsonElement(questao.alternativas)
    val termos = alternativas[0]
    val conceitos = alternativas[1]

    var termoSelecionado by rememberSaveable { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(questao.enunciado)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                termos.forEachIndexed { indice, termo ->
                    Button(
                        onClick = { termoSelecionado = indice },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (termoSelecionado == indice)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondary
                        )
                    ) { Text(termo) }
                }
            }

            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                conceitos.forEachIndexed { indice, conceito ->
                    Button(
                        onClick = {
                            termoSelecionado?.let { termo ->
                                onRelacionar(termo, indice)
                                termoSelecionado = null
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = termoSelecionado != null
                    ) { Text(conceito) }
                }
            }
        }

        relacoes.forEach { (termo, conceito) ->
            Text("${conceitos[conceito]}: ${termos[termo]}")
        }

        if (mostrarResultado) {

            val resposta = remember(questao) {
                DataRepository.jsonInstance
                    .decodeFromJsonElement<List<List<Int>>>(questao.resposta)
                    .associate { it[0] to it[1] }
            }


            if (relacoes == resposta ){
                Text("Você acertou")
            } else{
                Text("Você errou")
            }

        }
    }
}