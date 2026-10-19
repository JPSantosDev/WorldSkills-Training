package com.example.simuladod1.ui.screens

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.simuladod1.ui.models.Professor
import com.example.simuladod1.ui.preferences.TeacherPreference
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroProfessorScreen(
    preference: TeacherPreference,
    context: Context,
    professores:List<Professor>,
    onDashboard: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var maiorId by remember { mutableIntStateOf((professores.maxOfOrNull { it.id } ?: 0)+ 1 ) }


    fun validarForms(){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context,"Verifique seu Email", Toast.LENGTH_SHORT).show()
        }
        else if (nome.isEmpty() || nome.length < 3){
            Toast.makeText(context,"Nome deve conter pelo menos 3 caracteres", Toast.LENGTH_SHORT).show()
        }
        else if (!Patterns.PHONE.matcher(telefone).matches()){
            Toast.makeText(context,"Verifique seu Telefone", Toast.LENGTH_SHORT).show()
        }


    }

    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text("Professores - Novo")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onDashboard()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Button(
                    onClick = {
                        scope.launch {
                            preference.salvarProfessor(
                                Professor(
                                    id = maiorId++,
                                    nome = nome,
                                    email = email,
                                    telefone = telefone,
                                    descricao = descricao,
                                )
                            )
                            onDashboard()
                        }
                    }
                ) {
                    Text("Salvar")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        onDashboard()
                    }
                ) {
                    Text("X")
                }
            }
        }
    ) {pad->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(pad)
        ) {
            OutlinedTextField(
                label = {Text("Nome")},
                value = nome,
                onValueChange = { valor->
                    if (valor.length < 60){
                        nome = valor
                    } else {
                        Toast.makeText(context,"O limite é 60 caracteres",Toast.LENGTH_SHORT).show()
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )

            OutlinedTextField(
                label = {Text("E-mail")},
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            OutlinedTextField(
                label = {Text("Telefone")},
                value = telefone,
                onValueChange = { valor->
                    val numeros = valor.filter { it.isDigit() }.take(11)
                   telefone = when(numeros.length){
                        11 -> "(${numeros.substring(0,2)}) ${numeros.substring(2,7)} - ${numeros.substring(7,11)}"
                        10 -> "(${numeros.substring(0, 2)}) ${numeros.substring(2, 6)}-${numeros.substring(6, 10)}"
                        else -> numeros
                    }
                    telefone = numeros
                },
                placeholder = {Text("(99) 99999-9999")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                label = {Text("Descrição")},
                value = descricao,
                onValueChange = { descricao = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )

        }
    }
}