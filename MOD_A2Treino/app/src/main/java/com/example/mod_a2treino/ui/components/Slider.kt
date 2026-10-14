package com.example.mod_a2treino.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val coresNota = listOf(
    Color(0xFFD32F2F), // vermelho
    Color(0xFFF57C00), // laranja
    Color(0xFFFBC02D), // amarelo
    Color(0xFF8BC34A), // verde claro
    Color(0xFF388E3C)  // verde
)

private fun corDaNota(nota: Float): Color =
    coresNota[nota.toInt().coerceIn(0, 4)]

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvaliacaoSlider(nota: Float) {
    val icones = listOf(
        Icons.Filled.SentimentVeryDissatisfied,
        Icons.Filled.SentimentDissatisfied,
        Icons.Filled.SentimentNeutral,
        Icons.Filled.SentimentSatisfied,
        Icons.Filled.SentimentVerySatisfied
    )

    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            icones.forEachIndexed { i, icone ->
                Icon(icone, contentDescription = null, tint = coresNota[i], modifier = Modifier.size(40.dp))
            }
        }

        Slider(
            value = nota.coerceIn(0f, 5f),
            onValueChange = {},
            enabled = false, // só exibe a média; não é interativo
            valueRange = 0f..5f,
            thumb = {
                Box(Modifier.size(20.dp).background(corDaNota(nota), CircleShape))
            },
            track = {
                Row(Modifier.fillMaxWidth().height(6.dp)) {
                    coresNota.forEach { cor ->
                        Box(Modifier.weight(1f).fillMaxHeight().background(cor))
                    }
                }
            }
        )

        Text(
            text = "%.2f".format(nota),
            fontWeight = FontWeight.Bold
        )
    }
}