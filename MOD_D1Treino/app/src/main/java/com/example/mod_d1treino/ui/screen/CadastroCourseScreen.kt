package com.example.mod_d1treino.ui.screen

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mod_d1treino.ui.models.Categoria
import com.example.mod_d1treino.ui.models.Professor
import com.example.mod_d1treino.ui.repository.DataRepository
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCourseScreen(
    onHome: () -> Unit,
    repository: DataRepository
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var nomeCompleto by remember() { mutableStateOf("") }
    var nomeBreve by remember() { mutableStateOf("") }
    var visivel by remember { mutableStateOf(false) }
    var sumarioCurso by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf<Categoria?>(null) }
    var categorias by remember { mutableStateOf<List<Categoria>>(emptyList()) }
    var professores by remember { mutableStateOf<List<Professor>>(emptyList()) }
    var categoriaExpanded by remember { mutableStateOf(false) }
    var formatoExpanded by remember { mutableStateOf(false) }

    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }
    var mostrarDatePickerInicio by remember { mutableStateOf(false) }
    var mostrarDatePickerFim by remember { mutableStateOf(false) }

    val formatos = listOf<String>(
        "Atividade Única",
        "Formato Social",
        "Formato Tópicos",
        "Formato Semanal"
    )
    var formatoSelecionado by remember { mutableStateOf<String?>(null) }

    var datePickerStateFim = rememberDatePickerState()
    var datePickerStateInicio = rememberDatePickerState()

    LaunchedEffect(Unit) {
        categorias = repository.loadCategorias()
        professores = repository.loadProfessores()
    }

    fun formatarData(timestamp: Long?): String {
        if (timestamp != null) {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            return sdf.format(timestamp)
        } else {
            return ""
        }
    }

    if (mostrarDatePickerFim){
        DatePickerDialog(
            confirmButton = {
                TextButton(
                    onClick = {
                        dataFim = formatarData(datePickerStateFim.selectedDateMillis)
                        mostrarDatePickerFim = false
                    }
                ){Text("OK")}
            },
            onDismissRequest = { mostrarDatePickerFim = false},
            dismissButton = {
                TextButton(onClick = { mostrarDatePickerFim = false }) { Text("Cancelar") }
            }
        ){
            DatePicker(state = datePickerStateFim)
        }
    }
    if (mostrarDatePickerInicio){
        DatePickerDialog(
            confirmButton = {
                TextButton(
                    onClick = {
                        dataInicio = formatarData(datePickerStateInicio.selectedDateMillis)
                        mostrarDatePickerInicio = false
                    }
                ){Text("OK")}
            },
            onDismissRequest = { mostrarDatePickerInicio = false},
            dismissButton = {
                TextButton(onClick = { mostrarDatePickerInicio = false }) { Text("Cancelar") }
            }
        ){
            DatePicker(state = datePickerStateInicio)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cursos- Novo") },
                navigationIcon = { IconButton(onClick = onHome){
                    Icon(Icons.Default.Home,contentDescription = null)
                }
                }
            )
        }
    ){ pad->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(pad)
            .imePadding()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Nome Completo")
                    OutlinedTextField(
                        value = nomeCompleto,
                        onValueChange = { nomeCompleto = it },
                    )
                }

                item {
                    Text("Nome Breve")
                    OutlinedTextField(
                        value = nomeBreve,
                        onValueChange = { nomeBreve = it },
                    )
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        ExposedDropdownMenuBox(
                            expanded = categoriaExpanded,
                            onExpandedChange = { categoriaExpanded = !categoriaExpanded }
                        ) {
                            OutlinedTextField(
                                value = categoriaSelecionada?.nome ?: "",
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .menuAnchor(),
                                label = {
                                    Text("Categoria")
                                },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaExpanded) }
                            )

                            ExposedDropdownMenu(
                                expanded = categoriaExpanded,
                                onDismissRequest = { categoriaExpanded = false }
                            ) {
                                categorias.forEach { categoria ->
                                    DropdownMenuItem(
                                        text = { Text(categoria.nome) },
                                        onClick = {
                                            categoriaSelecionada = categoria
                                            categoriaExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                        Text("Visível")
                        Switch(
                            checked = visivel,
                            onCheckedChange = { visivel = it }
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                        ) {
                            OutlinedTextField(
                                trailingIcon = {
                                    IconButton(onClick = { mostrarDatePickerInicio = true }) {
                                        Icon(Icons.Default.CalendarMonth,contentDescription = null)
                                    }
                                },
                                readOnly = true,
                                onValueChange = {},
                                value = dataInicio,
                                label = { Text("Data Início") },
                            )
                        }
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)

                        ) {
                            OutlinedTextField(
                                trailingIcon = {
                                    IconButton(onClick = { mostrarDatePickerFim = true }) {
                                        Icon(Icons.Default.CalendarMonth,contentDescription = null)
                                    }
                                },
                                readOnly = true,
                                onValueChange = {},
                                value = dataFim,
                                label = { Text("Data Fim") },
                            )
                        }
                    }
                }
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        value = sumarioCurso,
                        onValueChange = { sumarioCurso = it },
                        label = { Text("Sumário do Curso") },
                    )
                }

                item {
                    ExposedDropdownMenuBox(
                        expanded = formatoExpanded,
                        onExpandedChange = { formatoExpanded = !formatoExpanded },
                    ) {
                        OutlinedTextField(
                            value = formatoSelecionado ?: "",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            label = { Text("Formato") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = formatoExpanded) }
                        )
                        ExposedDropdownMenu(
                            expanded = formatoExpanded,
                            onDismissRequest = { formatoExpanded = false }
                        ) {
                            formatos.forEach { formato ->
                                DropdownMenuItem(
                                    text = { Text(formato) },
                                    onClick = {
                                        formatoSelecionado = formato
                                        formatoExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}