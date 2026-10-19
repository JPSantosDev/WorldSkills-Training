package com.example.simuladod1.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.simuladod1.ui.models.Curso
import com.example.simuladod1.ui.models.Professor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatoriosScreen(
    cursos: List<Curso>,
    professores: List<Professor>,
    onHomeClick: () -> Unit,
    onCursosClick: () -> Unit,
    onProfessoresClick: () -> Unit
) {
    val context = LocalContext.current

    var cursosSelecionadosIds by remember {
        mutableStateOf<List<Int>>(emptyList())
    }

    var professoresSelecionadosIds by remember {
        mutableStateOf<List<Int>>(emptyList())
    }

    var mostrarModalCursos by remember { mutableStateOf(false) }
    var mostrarModalProfessores by remember { mutableStateOf(false) }

    var textoRelatorio by remember { mutableStateOf("") }

    val cursosSelecionados = cursos.filter {
        it.id in cursosSelecionadosIds
    }

    val professoresSelecionados = professores.filter {
        it.id in professoresSelecionadosIds
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Relatórios",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onHomeClick) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Ir para Dashboard"
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                }
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Salvar relatório em PDF"
                )
            }
        },

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = onCursosClick,
                    icon = {
                        Icon(
                            Icons.Default.MenuBook,
                            contentDescription = "Cursos"
                        )
                    },
                    label = { Text("Cursos") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onProfessoresClick,
                    icon = {
                        Icon(
                            Icons.Default.People,
                            contentDescription = "Professores"
                        )
                    },
                    label = { Text("Professores") }
                )

                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    enabled = false,
                    icon = {
                        Icon(
                            Icons.Default.Description,
                            contentDescription = "Relatórios"
                        )
                    },
                    label = { Text("Relatórios") }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Cursos selecionados
            Text(
                text = "Curso",
                style = MaterialTheme.typography.labelLarge
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp, max = 100.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (cursosSelecionados.isEmpty()) {
                            Text(
                                "Nenhum curso selecionado",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            cursosSelecionados.forEach { curso ->
                                InputChip(
                                    selected = true,
                                    onClick = {},
                                    label = { Text(curso.nomeCompleto) }
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = { mostrarModalCursos = true }
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Selecionar cursos"
                        )
                    }
                }
            }
            Text(
                text = "Professor",
                style = MaterialTheme.typography.labelLarge
            )

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp, max = 100.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (professoresSelecionados.isEmpty()) {
                            Text(
                                "Nenhum professor selecionado",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            professoresSelecionados.forEach { professor ->
                                InputChip(
                                    selected = true,
                                    onClick = {},
                                    label = { Text(professor.nome) }
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = { mostrarModalProfessores = true }
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Selecionar professores"
                        )
                    }
                }
            }


            Button(
                onClick = {

                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Gerar Relatório")
            }


            OutlinedTextField(
                value = textoRelatorio,
                onValueChange = {},
                readOnly = true,
                label = { Text("Relatório") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }

    if (mostrarModalCursos) {
        SelecaoMultiplaCursoDialog(
            titulo = "Selecionar cursos",
            cursos = cursos,
            idsSelecionados = cursosSelecionadosIds,
            onCancelar = {
                mostrarModalCursos = false
            },
            onConfirmar = { ids ->
                cursosSelecionadosIds = ids
                mostrarModalCursos = false
            }
        )
    }
    if (mostrarModalProfessores) {
        SelecaoMultiplaProfDialog (
            titulo = "Selecionar professores",
            professores = professores,
            idsSelecionados = professoresSelecionadosIds,
            onCancelar = {
                mostrarModalProfessores = false
            },
            onConfirmar = { ids ->
                professoresSelecionadosIds = ids
                mostrarModalProfessores = false
            }
        )
    }
}

@Composable
private fun SelecaoMultiplaCursoDialog(
    titulo: String,
    cursos: List<Curso>,
    idsSelecionados: List<Int>,
    onCancelar: () -> Unit,
    onConfirmar: (List<Int>) -> Unit,
) {
    var selecaoTemporaria by remember(idsSelecionados) {
        mutableStateOf(idsSelecionados)
    }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(titulo) },
        text = {
            if (cursos.isEmpty()) {
                Text("Nenhum curso disponível.")
            } else {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    items(
                        cursos,
                        key = {it.id}
                    ) { curso ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = curso.id in selecaoTemporaria,
                                onCheckedChange = { marcado ->
                                    selecaoTemporaria =
                                        if (marcado) {
                                            selecaoTemporaria + curso.id
                                        } else {
                                            selecaoTemporaria - curso.id
                                        }
                                }
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = curso.nomeCompleto,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                if (curso.descricao.isNotBlank()) {
                                    Text(
                                        text = curso.descricao,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmar(selecaoTemporaria) }
            ) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}
@Composable
private fun SelecaoMultiplaProfDialog(
    titulo: String,
    professores: List<Professor>,
    idsSelecionados: List<Int>,
    onCancelar: () -> Unit,
    onConfirmar: (List<Int>) -> Unit,
) {
    var selecaoTemporaria by remember(idsSelecionados) {
        mutableStateOf(idsSelecionados)
    }

    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(titulo) },
        text = {
            if (professores.isEmpty()) {
                Text("Nenhum curso disponível.")
            } else {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    items(
                        professores,
                        key = {it.id}
                    ) { professor ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = professor.id in selecaoTemporaria,
                                onCheckedChange = { marcado ->
                                    selecaoTemporaria =
                                        if (marcado) {
                                            selecaoTemporaria + professor.id
                                        } else {
                                            selecaoTemporaria - professor.id
                                        }
                                }
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                            ) {
                                Text(
                                    text = professor.nome,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                if (professor.descricao.isNotBlank()) {
                                    Text(
                                        text = professor.descricao,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmar(selecaoTemporaria) }
            ) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}


