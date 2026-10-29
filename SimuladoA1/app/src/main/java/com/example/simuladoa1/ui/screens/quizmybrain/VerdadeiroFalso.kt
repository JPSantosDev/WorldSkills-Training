package com.example.simuladoa1.ui.screens.quizmybrain

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.serialization.json.decodeFromJsonElement

@Composable
fun VerdadeiroFalso(
    questao: DadosCurso,
    alternativaSelecionada: Int?,
    onAlternativaSelecionada: (Int) -> Unit,
    mostrarResultado: Boolean,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    LaunchedEffect(questao.id) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            TiltObserver(context).inclinacaoLateral
                .selecoesPorInclinacao()
                .collect {
                    onAlternativaSelecionada(it)
                }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Text(questao.enunciado)

        Row {
            listOf("Falso", "Verdadeiro").forEachIndexed { indice, texto ->
                Button(
                    onClick = { onAlternativaSelecionada(indice) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (alternativaSelecionada == indice) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                ) {
                    Text(texto)
                }
            }
        }
        if (mostrarResultado) {

            val resposta = DataRepository.jsonInstance.decodeFromJsonElement<Int>(questao.resposta)

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