package com.example.mod_d1treino.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.mod_d1treino.ui.components.CursoModal
import com.example.mod_d1treino.ui.components.ProfessorModal
import com.example.mod_d1treino.ui.models.Curso
import com.example.mod_d1treino.ui.models.Professor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatorioScreen(
    professores: List<Professor>,
    cursos: List<Curso>,
    onHome: () -> Unit,
    onGerarRelatorio: () -> Unit,
    onCursos: () -> Unit,
    onTeachers: () -> Unit,
    onSalvar: () -> Unit
) {


    val professoresSelecionados = remember { mutableStateListOf<Professor>() }
    val cursosSelecionados = remember { mutableStateListOf<Curso>() }
    var relatorioGerado by remember { mutableStateOf(false) }

    var mostrarProfModal by remember { mutableStateOf(true) }
    var mostrarCursoModal by remember { mutableStateOf(false) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Relatórios") },
                navigationIcon = {
                    IconButton(onHome) {
                        Icon(Icons.Default.Home,contentDescription = null)
                    }
                }
            )
        },

        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onCursos, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Book,contentDescription = null)
                    }
                    IconButton(onClick = onTeachers, modifier = Modifier
                        .weight(1f)
                        .testTag("teachersBtn")) {
                        Icon(Icons.Default.AccountCircle,contentDescription = null)
                    }
                    IconButton(onClick = {}, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Note,contentDescription = null,tint = Color.Gray)
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick ={
                    relatorioGerado = false
                    onSalvar()
                }

            ) {
                Icon(Icons.Default.Save,contentDescription = null)
            }
        }

    ) { pad->
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(pad),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.White,
                    disabledBorderColor = Color.White,
                    disabledPlaceholderColor = Color.White,
                    disabledContainerColor = Color.Transparent,
                    disabledLabelColor = Color.White,
                    cursorColor = Color.White
                ),
                modifier = Modifier.clickable{
                    mostrarCursoModal = true
                },
                label = {Text("Curso")},
                enabled = false,
                readOnly = true,
                value = if (cursosSelecionados.isEmpty())
                    "Selecionar Curso"
                else
                    cursosSelecionados.joinToString(", "){it.nomeCompleto},
                onValueChange = {},
            )

            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.White,
                    disabledBorderColor = Color.White,
                    disabledPlaceholderColor = Color.White,
                    disabledContainerColor = Color.Transparent,
                    disabledLabelColor = Color.White,
                    cursorColor = Color.White

                ),
                label = {Text("Professor")},
                modifier = Modifier.clickable {
                    mostrarProfModal = true
                },
                readOnly = true,
                enabled = false,
                value = if (professoresSelecionados.isEmpty())
                    "Selecionar Professor"
                else
                    professoresSelecionados.joinToString(", "){it.nome},
                onValueChange = {},
            )

            Button(
                onClick = {
                    relatorioGerado = true
                    onGerarRelatorio()
                }
            ) {
                Text("Gerar Relatório")
            }

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(300.dp)
                    .border(1.dp, Color.Black),
                contentAlignment = Alignment.Center,
            ){
                Column() {
                    if (relatorioGerado){
                        Text("Professores: \n")
                        Text(professoresSelecionados.joinToString("\n, \n"))

                        Text("Cursos: \n")
                        Text(cursosSelecionados.joinToString("\n, \n"))

                    }
                    else{
                        Text("O relatório será gerado aqui")
                    }
                }
            }

            if(mostrarProfModal){
                ProfessorModal (
                    professores = professores,
                    onDismiss = {
                        mostrarProfModal = false
                    },
                    professoresSelecionados = professoresSelecionados,
                    removeProfessor = { professoresSelecionados.remove(it) },
                    addProfessor = { professoresSelecionados.add(it) },
                )
            }
            if (mostrarCursoModal){
                CursoModal(
                    cursos = cursos,
                    cursosSelecionados = cursosSelecionados,
                    addCurso = { cursosSelecionados.add(it) },
                    removeCurso = { cursosSelecionados.remove(it) },
                    onDismiss = { mostrarCursoModal = false }
                )
            }
        }
    }
}