package com.example.simuladod1.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.simuladod1.ui.components.ProfessoresDialog
import com.example.simuladod1.ui.models.Categoria
import com.example.simuladod1.ui.models.Professor
import com.example.simuladod1.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCursoScreen(
    categorias: List<Categoria>,
    professores: List<Professor>,
    onDashboard: () -> Unit
){
    val formatoOptions = listOf(
        "Atividade Única",
        "Formato Social",
        "Formato tópicos",
        "Formato Semanal"
    )

    var nomeCompleto by remember{ mutableStateOf("") }
    var nomeBreve by remember{ mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }

    val categoriaSelected = remember { mutableStateListOf<Categoria>() }
    val professorSelected = remember { mutableStateListOf<Professor>() }
    var formatoSelected by remember { mutableStateOf("") }


    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }

    val dataInicioState = rememberDatePickerState()
    val dataFimState = rememberDatePickerState()

    var sumario by remember { mutableStateOf("") }

    var categoriaExpanded by remember { mutableStateOf(false) }
    var formatoExpanded by remember { mutableStateOf(false) }
    var mostrarProfessores by remember { mutableStateOf(false) }
    var professoresSalvos by remember { mutableStateOf<List<Professor>>(emptyList()) }
    var professorComLixeira by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Cursos-Novo")
                },
                navigationIcon = {
                    IconButton(
                        onClick ={
                            onDashboard()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home ,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ){ pad->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pad),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nomeCompleto,
                onValueChange = {nomeCompleto = it },
                label = { Text("Nome Completo") },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nomeBreve,
                onValueChange = {nomeBreve = it },
                label = { Text("Nome Breve") },
                singleLine = true
            )

            Row {
                ExposedDropdownMenuBox(
                    expanded = categoriaExpanded,
                    onExpandedChange = {categoriaExpanded = it}
                ) {
                    OutlinedTextField(
                        value = categoriaSelected.joinToString(", ") { it.nome } ,
                        onValueChange = {},
                        readOnly = true,
                        label = {Text("Categoria")},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = categoriaExpanded
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = categoriaExpanded,
                        onDismissRequest = {
                            categoriaExpanded = false
                        }
                    ) {
                        categorias.forEach { categoria->
                            DropdownMenuItem(
                                text = {Text(categoria.nome)},
                                onClick = {
                                    categoriaSelected.add(categoria)
                                }
                            )
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .weight(1f),
                ) {
                    Text("Visível")
                    Switch(
                        checked = visible,
                        onCheckedChange = {valor-> visible = valor}
                    )
                }
            }

            OutlinedTextField(
                label = {Text("Sumário do Curso")},
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                value = sumario,
                onValueChange = {sumario = it}
            )

            ExposedDropdownMenuBox(
                expanded = formatoExpanded,
                onExpandedChange = {formatoExpanded = it}
            ) {
                OutlinedTextField(
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = formatoExpanded,
                        )
                    },
                    label = {Text("Formato")},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    value = formatoSelected,
                    onValueChange = {formatoSelected = it}
                )
                ExposedDropdownMenu(
                    expanded = formatoExpanded,
                    onDismissRequest = { formatoExpanded = false }
                ) {
                    formatoOptions.forEach { opcao->
                        DropdownMenuItem(
                            text = {Text(opcao)},
                            onClick = {
                                formatoSelected = opcao
                                formatoExpanded = false
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Professores", style = Typography.titleLarge)
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.border(1.dp, Color.Gray)
                ){
                    IconButton(
                        onClick = {
                            mostrarProfessores = true
                        }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }

            }
            LazyColumn() {
                items(professoresSalvos, key = {it.id}){professor->
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .pointerInput(professor.id){
                                detectHorizontalDragGestures { change, dragAmount ->
                                    if (dragAmount < 0){
                                        professorComLixeira = professor.id
                                    } else {
                                        professorComLixeira = null
                                    }
                                }
                            },
                        ) {
                        Text(professor.nome)
                        if (professorComLixeira == professor.id){
                            IconButton(
                                onClick = {
                                    professoresSalvos = professoresSalvos - professor
                                }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }

        if (mostrarProfessores){
            ProfessoresDialog(
                professores = professores,
                selecionadosIniciais = professoresSalvos,
                onDismiss = { mostrarProfessores = false },
                onSalvar = { professoresSalvos = it}
            )
        }
    }
}