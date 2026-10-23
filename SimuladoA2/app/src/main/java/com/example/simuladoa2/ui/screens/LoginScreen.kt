package com.example.simuladoa2.ui.screens

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.simuladoa2.ui.repository.DataRepository
import com.example.simuladoa2.ui.repository.TokenManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    context: Context,
    onLogin: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var senhaVisivel by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            singleLine = true,
            value = email,
            onValueChange = { valor->
                val emailFiltrado = valor.filter { it.isLetter() || it == '@' || it =='.' }
                email = emailFiltrado
            }
        )

        OutlinedTextField(
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        senhaVisivel = !senhaVisivel
                    }
                ) {
                    Icon(
                        if (senhaVisivel) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            value = password,
            onValueChange = {
                password = it
            },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation()
        )


        Row{
            Checkbox(
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color.Black,
                    checkedColor = Color.Black
                ),
                modifier = Modifier.padding(end = 8.dp),
                checked = rememberMe,
                onCheckedChange = {
                    rememberMe = it
                }
            )
            Text("Lembrar de mim")
        }
        Button(
            onClick = {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(context,"Verifique seu email", Toast.LENGTH_SHORT).show()
                } else {
                    scope.launch {
                        val token = DataRepository.generateToken(email,password)
                        TokenManager.editToken(token)
                        val valid = DataRepository.validateToken(token).valid
                        if (valid){
                            onLogin()
                        }
                    }
                }
            }
        ) {
            Text("Acessar Sistema")
        }
    }
}