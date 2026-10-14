package com.example.mod_a2treino.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp

@Composable
fun StarProgress(rating: Double){
    Row() {
        repeat(5){index->

            val fraction = (rating-index).coerceIn(0.0,1.0).toFloat()

            Box(Modifier.size(24.dp)){
                Icon(Icons.Filled.Star,contentDescription = null, tint = Color.LightGray, modifier = Modifier.fillMaxSize())
                Icon(Icons.Filled.Star,contentDescription = null, tint = Color.Yellow, modifier = Modifier.fillMaxSize()
                    .drawWithContent{
                        clipRect(right = this.size.width * fraction){ this@drawWithContent.drawContent()}
                    }
                )
            }
        }
    }
}