package com.example.mod_d1treino.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mod_d1treino.ui.models.Curso

@Composable
fun NoGridModeCursos(
    modifier: Modifier = Modifier,
    curso: Curso,
    onTap: () -> Unit,
    onLongPress: () -> Unit,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("curso_${curso.id}")
            .pointerInput(curso.id) {
                detectTapGestures(
                    onTap = { if (curso.id == 5) onTap() },
                    onLongPress = { if (curso.id == 5) onLongPress() }
                )
            },
        verticalArrangement = Arrangement.Center,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(progress = 1f, color = Color.Gray)
                CircularProgressIndicator(
                    progress = curso.porcentagem.toFloat(),
                    color = Color.Red,
                )
                Text("${curso.porcentagem * 100}%")
            }
            Text(curso.nomeBreve, modifier = Modifier.padding(4.dp))
        }
    }
}