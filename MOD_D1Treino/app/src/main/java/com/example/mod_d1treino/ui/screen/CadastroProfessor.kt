package com.example.mod_d1treino.ui.screen

import android.content.Context
import android.net.Uri
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.mod_d1treino.ui.components.ImageField
import com.example.mod_d1treino.ui.models.Professor
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroProfessor(
    onHome: () -> Unit,
    professores: MutableList<Professor>,
    onDashboard: () -> Unit,
    context: Context
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var idMaior by remember { mutableIntStateOf(0) }
    var imagemUri by remember { mutableStateOf<Uri?>(null)}
    var telefoneField by remember { mutableStateOf(TextFieldValue("")) }


    idMaior = professores.maxOf { it.id }

    fun validarCampos(): String?{
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "Email inválido"
        } else if (!Patterns.PHONE.matcher(telefone).matches()){
            return "Telefone inválido"
        } else if (nome.length>60){
            return "Nome deve conter no máximo 60 caracteres"
        } else if (descricao.length>200) {
            return "Descricao deve conter no máximo 200 caracteres"
        } else if (nome.length<3){
            return "Nome deve conter pelo menos 3 caracteres"
        }
        else return null
    }

    fun formatarTelefone(entrada: String): String {
        val digitos = entrada.filter { it.isDigit() }.take(11)
        return buildString {
            digitos.forEachIndexed { index, ch ->
                if (index == 0) append("(")
                if (index == 2) append(")")
                if (index == 7) append("-")
                append(ch)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Professores") },
                navigationIcon = { IconButton(onClick = onHome) { Icon(Icons.Default.Home,contentDescription = null) }
                },
            )
        },

        ){pad->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(pad),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.testTag("teacherName"),
                label = {Text("Nome")},
                value = nome,
                onValueChange = { nome = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )

            OutlinedTextField(
                modifier = Modifier.testTag("teacherEmail"),
                label = {Text("E-mail")},
                value = email,
                onValueChange = {email = it},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            OutlinedTextField(
                label = { Text("Telefone") },
                value = telefoneField,
                onValueChange = { novo ->
                    val formatado = formatarTelefone(novo.text)
                    telefoneField = TextFieldValue (
                        text = formatado,
                        selection = TextRange(formatado.length)   // cursor no fim
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField (
                label = {Text("Descrição")},
                value = descricao,
                onValueChange = {descricao = it}
            )

            ImageField (
                uri = imagemUri,
                onUriChange = {imagemUri = it }
            )

            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    modifier = Modifier.testTag("saveBtn"),
                    onClick = {
                        val erro = validarCampos()
                        if (erro != null){
                            Toast.makeText(context,erro, Toast.LENGTH_SHORT ).show()
                        } else {
                            professores.add(
                                Professor(
                                    id = idMaior+1,
                                    nome = nome,
                                    email = email,
                                    telefone = telefone,
                                    descricao = descricao
                                )
                            )
                            onDashboard()
                        }
                    }
                ) {
                    Text("Salvar")
                }
                Button(
                    onClick = onDashboard
                ) {
                    Text("X")
                }
            }
        }
    }
}