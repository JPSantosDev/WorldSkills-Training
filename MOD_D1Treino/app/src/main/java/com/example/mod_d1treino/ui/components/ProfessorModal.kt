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
import com.example.mod_d1treino.ui.models.Professor

@Composable
fun ProfessorModal(
    professores: List<Professor>,
    onDismiss: () -> Unit,
    professoresSelecionados: List<Professor>,
    removeProfessor: (Professor) -> Unit,
    addProfessor: (Professor) -> Unit
){

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(Modifier.fillMaxWidth()) {

                Text("Professor")
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        Icons.Default.Cancel,
                        contentDescription = null
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Fechar")
            }
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(professores, key = {it.id }){ professor->
                        Row(
                            modifier = Modifier.testTag(professor.nome),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(professor.nome)

                            if (professoresSelecionados.contains(professor)){
                                IconButton(
                                    onClick = { removeProfessor(professor) }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            }  else {
                                IconButton(
                                    onClick = { addProfessor(professor) }
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