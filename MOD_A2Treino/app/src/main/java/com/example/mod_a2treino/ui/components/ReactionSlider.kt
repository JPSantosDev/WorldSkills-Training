package com.example.mod_a2treino.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReactionSlider(
    progress: Float
) {

    val cores = listOf(
        Color.Red,
        Color(0xFFF47721),
        Color.Yellow,
        Color.Green,
        Color(0xFF2E963D)
    )

    val icones = listOf(
        Icons.Default.SentimentVeryDissatisfied,
        Icons.Default.SentimentDissatisfied,
        Icons.Default.SentimentNeutral,
        Icons.Default.SentimentSatisfied,
        Icons.Default.SentimentVerySatisfied,
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ){
            val largura = maxWidth
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                icones.forEachIndexed { index, icone ->
                    Icon(
                        imageVector = icone,
                        contentDescription = null,
                        tint = cores[index],
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .align(Alignment.BottomCenter)
            ) {
                cores.forEach { cor ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(cor)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(
                        x = largura * (progress.coerceIn(0f, 5f) / 5f) - 6.dp,
                        y = 6.dp
                    )
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )
        }
    }
}