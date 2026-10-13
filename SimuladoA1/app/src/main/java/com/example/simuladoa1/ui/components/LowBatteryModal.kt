package com.example.simuladoa1.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.simuladoa1.R
import com.example.simuladoa1.ui.theme.Typography

@Composable
fun LowBatteryModal(onDismiss: () -> Unit) {
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
                Icon(painter = painterResource(R.drawable.bateria),contentDescription = null)
                Text("Atenção", style = Typography.headlineSmall)
                Text("Bateria baixa")
            }
        }

    )
}