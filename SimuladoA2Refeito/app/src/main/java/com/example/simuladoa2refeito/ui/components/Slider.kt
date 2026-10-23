package com.example.simuladoa2refeito.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReactSlider(
    progress: Float
){
    val progressSlider = progress.coerceIn(0f,1f)
    val sliderState = rememberSliderState(
        value = progressSlider,
    )
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Icon(Icons.Default.Star,contentDescription = null)
                Icon(Icons.Default.Star,contentDescription = null)
                Icon(Icons.Default.Star,contentDescription = null)
                Icon(Icons.Default.Star,contentDescription = null)
                Icon(Icons.Default.Star,contentDescription = null)
            }
            Slider(
                colors = SliderDefaults.colors(
                    disabledThumbColor = Color.Red,
                    disabledActiveTickColor = Color.Red,
                    disabledActiveTrackColor = Color.Red,
                    disabledInactiveTrackColor = Color.White,
                    disabledInactiveTickColor = Color.White

                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                state = sliderState
            )
        }

}