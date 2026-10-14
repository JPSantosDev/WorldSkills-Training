package com.example.mod_a2treino.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErroPopup(error: String, tentativa: Int, dismiss: () -> Unit, retry: () -> Unit){

    AlertDialog(
        title = { Text("Ocorreu um erro") },
        text = {Text(error)},
        onDismissRequest = dismiss,
        confirmButton = { TextButton(onClick = retry) { Text("Tentar novamente")} },
        dismissButton = { TextButton(onClick = dismiss ){Text("Fechar")} },


        )
}