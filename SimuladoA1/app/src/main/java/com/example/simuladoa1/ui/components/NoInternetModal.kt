package com.example.simuladoa1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NoInternetModal(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Fechar")
            }
        },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Text("Sem conexão com internet, seus dados podem não estar completamente salvos")
            }
        }

    )
}