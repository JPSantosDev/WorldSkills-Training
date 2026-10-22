package com.example.mod_d1treino.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.mod_d1treino.ui.models.Curso

@Composable
fun CursoModal(
    cursos: List<Curso>,
    cursosSelecionados: List<Curso>,
    addCurso: (Curso) -> Unit,
    removeCurso: (Curso) -> Unit,
    onDismiss: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(Modifier.fillMaxWidth()) {
                Text("Curso")
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onDismiss) {
                    Icon(
                        Icons.Default.Cancel,
                        contentDescription = null
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Fechar")
            }
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                LazyColumn(

                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(cursos, key = {it.id }){ curso->
                        Row(
                            modifier = Modifier.testTag(curso.nomeCompleto),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(curso.nomeCompleto)

                            if (cursosSelecionados.contains(curso)){
                                IconButton(
                                    onClick = { removeCurso(curso) }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }  else {
                                IconButton(
                                    onClick = { addCurso(curso) }
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}