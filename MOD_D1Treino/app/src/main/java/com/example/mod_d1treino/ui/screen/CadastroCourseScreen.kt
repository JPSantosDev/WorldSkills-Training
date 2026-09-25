package com.example.mod_d1treino.ui.screen


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mod_d1treino.ui.models.Categoria
import com.example.mod_d1treino.ui.models.Curso
import com.example.mod_d1treino.ui.models.Professor
import com.example.mod_d1treino.ui.preferences.CoursePreferences
import com.example.mod_d1treino.ui.repository.DataRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCourseScreen(
    onHome: () -> Unit,
    repository: DataRepository,
    preferences: CoursePreferences,
    onDashboard: () -> Unit
) {

    val cursos by preferences.cursos.collectAsStateWithLifecycle(emptyList())

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var nomeCompleto by remember() { mutableStateOf("") }
    var nomeBreve by remember() { mutableStateOf("") }
    var visivel by remember { mutableStateOf(false) }
    var sumarioCurso by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf<Categoria?>(null) }
    var categorias by remember { mutableStateOf<List<Categoria>>(emptyList()) }
    var professores by remember { mutableStateOf<List<Professor>>(emptyList()) }
    var selectedProfessores = remember { mutableStateListOf<Professor>() }
    var categoriaExpanded by remember { mutableStateOf(false) }
    var formatoExpanded by remember { mutableStateOf(false) }
    var buscaAddProfessor by remember { mutableStateOf("") }

    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }
    var mostrarDatePickerInicio by remember { mutableStateOf(false) }
    var mostrarDatePickerFim by remember { mutableStateOf(false) }
    var mostrarAddProfessor by remember { mutableStateOf(false) }

    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    sdf.timeZone = TimeZone.getTimeZone("UTC")


    val formatos = listOf<String>(
        "Atividade Única",
        "Formato Social",
        "Formato Tópicos",
        "Formato Semanal"
    )
    var formatoSelecionado by remember { mutableStateOf<String?>(null) }

    var datePickerStateFim = rememberDatePickerState(
    )
    var datePickerStateInicio = rememberDatePickerState(

        initialSelectedDate = LocalDate.now()
    )

    fun formatarData(timestamp: Long?): String {
        if (timestamp != null) {

            return sdf.format(timestamp)
        } else {
            return ""
        }
    }
    LaunchedEffect(Unit) {
        categorias = repository.loadCategorias()
        professores = repository.loadProfessores()
    }

    fun validarCampos(): String?{


        if (nomeCompleto.trim().length !in 10..50){
            return "Nome completo deve ter entre 10 e 50 caracteres"
        }

        if (nomeBreve.trim().length !in 3..15 ){
            return "Nome breve deve conter entre 3 e 15 caracteres no máximo"
        }

        if (sumarioCurso.trim().length>200){
            return "Sumário do curso deve conter 200 caracteres no máximo"
        }

        if (categoriaSelecionada == null){
            return "Selecione uma categoria"
        }
        if (dataInicio.isBlank()){
            return "Selecione uma data de início"
        }
        if (dataFim.isBlank()){
            return "Selecione uma data de fim"
        }
        if (selectedProfessores.isEmpty()){
            return "Selecione pelo menos um professor"
        }




        //Date Validations

        val hoje = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            set(Calendar.HOUR_OF_DAY,0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
            set(Calendar.MILLISECOND,0)
        }.time

        if (sdf.parse(dataInicio).before(hoje)){
            return "Data de início não pode ser anterior a hoje"
        }
        if (sdf.parse(dataFim).before(sdf.parse(dataInicio))){
            return "Data de fim não pode ser anterior a data de início"
        }

        return null
    }



    if (mostrarAddProfessor) {
        AlertDialog(
            onDismissRequest = { mostrarAddProfessor = false },
            dismissButton = {
                IconButton(
                    onClick = { mostrarAddProfessor = false }
                ) { Icon(Icons.Default.Close, contentDescription = null) }
            },
            confirmButton = {},
            text = {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(8.dp)

                ) {
                    Text("Professores", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = buscaAddProfessor,
                        onValueChange = { buscaAddProfessor = it },
                        trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                    )
                    LazyColumn() {
                        items(professores) { professor ->
                            Row() {
                                Text(professor.nome)
                                IconButton(onClick = {
                                    if (selectedProfessores.contains(professor))

                                        selectedProfessores.remove(professor)
                                    else
                                        if (selectedProfessores.size>=5)
                                            Toast.makeText(context,"Máximo de 5 professores por curso",Toast.LENGTH_SHORT).show()
                                        else
                                            selectedProfessores.add(professor)
                                }
                                ) {
                                    if (selectedProfessores.contains(professor))
                                        Icon(Icons.Default.Remove, contentDescription = null)
                                    else
                                        Icon(Icons.Default.Add, contentDescription = null)

                                }
                            }
                        }
                    }
                }
            }
        )
    }

    if (mostrarDatePickerFim) {
        AlertDialog(
            onDismissRequest = { mostrarDatePickerFim = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDatePickerFim = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePickerFim = false }) { Text("Cancelar") }
            },
            text = {
                Column {
                    Text("Selecione a data de fim")
                    OutlinedTextField(
                        value = dataFim,
                        onValueChange = { dataFim = it },
                        label = { Text("DD/MM/YYYY") },
                        modifier = Modifier.testTag("inputDataFim")
                    )
                }
            }
        )
    }

    if (mostrarDatePickerInicio) {
        AlertDialog(
            onDismissRequest = { mostrarDatePickerInicio = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDatePickerInicio = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePickerInicio = false }) { Text("Cancelar") }
            },
            text = {
                Column {
                    Text("Selecione a data de início")
                    OutlinedTextField(
                        value = dataInicio,
                        onValueChange = { dataInicio = it },
                        label = { Text("DD/MM/YYYY") },
                        modifier = Modifier.testTag("inputDataInicio")
                    )
                }
            }
        )
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cursos- Novo") },
                navigationIcon = {
                    IconButton(onClick = onHome) {
                        Icon(Icons.Default.Home, contentDescription = null)
                    }
                }
            )
        }
    ) { pad ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .imePadding()
                .testTag("mainColumn")
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Nome Completo")
                        OutlinedTextField(
                            value = nomeCompleto,
                            onValueChange = { nomeCompleto = it },
                            modifier = Modifier.testTag("nomeCompletoCurso")
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Nome Breve")
                        OutlinedTextField(
                            value = nomeBreve,
                            onValueChange = { nomeBreve = it },
                            modifier = Modifier.testTag("nomeBreveCurso")
                        )
                    }
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
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
                                    .menuAnchor()
                                    .testTag("categoriaCurso"),
                                label = {
                                    Text("Categoria")
                                },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaExpanded) },

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
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            OutlinedTextField(
                                trailingIcon = {
                                    IconButton(onClick = { mostrarDatePickerInicio = true }, modifier = Modifier.testTag("btnDateInicio")) {
                                        Icon(Icons.Default.CalendarMonth, contentDescription = null)
                                    }
                                },
                                readOnly = true,
                                onValueChange = {},
                                value = dataInicio,
                                label = { Text("Data Início") },
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)

                        ) {
                            OutlinedTextField(
                                trailingIcon = {
                                    IconButton(onClick = { mostrarDatePickerFim = true }, modifier = Modifier.testTag("btnDateFim")) {
                                        Icon(Icons.Default.CalendarMonth, contentDescription = null)
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
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            value = sumarioCurso,
                            onValueChange = { sumarioCurso = it },
                            label = { Text("Sumário do Curso") },
                        )
                    }
                }

                item {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
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

                item {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Professores")
                        IconButton(
                            onClick = {
                                mostrarAddProfessor = true
                            }

                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(8.dp)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            items(selectedProfessores) { professor ->
                                var showThrash by remember { mutableStateOf(false) }
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .pointerInput(professor) {
                                            detectHorizontalDragGestures { _, dragAmount ->
                                                if (dragAmount < -10) {
                                                    showThrash = true
                                                }
                                            }
                                        }.testTag("professor_${professor.id}")
                                ) {
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(professor.nome)
                                        if (showThrash) {
                                            IconButton(
                                                onClick = {
                                                    selectedProfessores.remove(professor)
                                                    showThrash = false
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
                    }
                }
                item {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Button(onClick = {
                            val erro = validarCampos()
                            if (erro != null){
                                Toast.makeText(context,erro,Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            scope.launch {
                                val maxId = cursos.maxOfOrNull { it.id } ?: 0


                                val novoCurso = Curso(
                                    id = maxId + 1,
                                    nomeCompleto = nomeCompleto.trim(),
                                    nomeBreve = nomeBreve.trim(),
                                    categoriaId = categoriaSelecionada!!.id,
                                    visivel = visivel,
                                    dataInicio = dataInicio,
                                    dataFim = dataFim,
                                    descricao = sumarioCurso.trim(),
                                    formato = formatoSelecionado!!,
                                    professoresId = selectedProfessores.map { it.id },
                                    porcentagem = 0.0,
                                )
                                preferences.salvarCursos(novoCurso)
                                onDashboard()
                            }
                        },
                            modifier = Modifier.testTag("btnSave")

                        ) {
                            Text("Salvar")
                        }
                        Button(onClick = {
                            onHome()
                        }) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}