package com.example.simuladod1.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.simuladod1.ui.models.Professor

@Composable
fun ProfessoresDialog(
    professores: List<Professor>,
    onDismiss: () -> Unit,
    onSalvar: (List<Professor>) -> Unit,
    selecionadosIniciais:List<Professor> = emptyList()
){
    val context = LocalContext.current
    var busca by remember { mutableStateOf("") }

    val professoresFiltrados = professores.filter { it.nome.contains(busca, ignoreCase = true) }

    val professoresSelecionados = rememberSaveable() {
        mutableStateListOf<Professor>().apply {
            addAll(selecionadosIniciais)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(8.dp),
        title = {
            Row(Modifier.fillMaxWidth()) {
                Text("Professores")
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null
                    )
                }

            }
        },

        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = busca,
                    onValueChange = {busca = it},
                    placeholder = {Text("Busca")},
                    trailingIcon = {Icon(Icons.Default.Search,contentDescription = null)}
                )

                LazyColumn() {
                    items(
                        items = professoresFiltrados,
                        key = {it.id}){professor->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                            ,
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(professor.nome)
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick ={
                                    if (professoresSelecionados.contains(professor)){
                                        professoresSelecionados.remove(professor)
                                    } else {
                                        if (professoresSelecionados.size < 5){
                                            professoresSelecionados.add(professor)
                                        }
                                        else Toast.makeText(context,"Escolha no máximo somente 5 professores",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            ) {
                                Icon(
                                    if (professoresSelecionados.contains(professor)) Icons.Default.Remove
                                    else Icons.Default.Add,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Text("Selecionados: ${professoresSelecionados.size}")
            Button(
                onClick = {
                    onSalvar(professoresSelecionados.toList())
                    onDismiss()
                }
            ) {


                Text("Salvar")
            }
        },

        )
}