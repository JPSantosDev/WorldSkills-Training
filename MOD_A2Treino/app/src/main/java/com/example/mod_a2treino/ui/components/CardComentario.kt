package com.example.mod_a2treino.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mod_a2treino.ui.models.Comment

@Composable
fun ComentarioCard(comentario: Comment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(comentario.autor_nome, fontWeight = FontWeight.Bold)
            Text(comentario.comentario)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* curtir */ }) {
                    Icon(Icons.Default.ThumbUp, contentDescription = "Curtir")
                }
                Text("0")
                IconButton(onClick = { /* responder */ }) {
                    Icon(Icons.AutoMirrored.Filled.Reply, contentDescription = "Responder")
                }
            }
        }
    }
}