package com.example.mod_ctreino.ui.components

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PinField(
    pin: String,
    onPinChange: (String) -> Unit,
    isError: Boolean = false,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        Row() {
            repeat(4) { i ->
                val caractere = pin.getOrNull(i)
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, if (isError) Color.Red else Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(caractere?.toString() ?: "")
                }
            }
        }

        BasicTextField(
            cursorBrush = SolidColor(Color.Transparent),
            textStyle = TextStyle(Color.Transparent),
            modifier = Modifier
                .matchParentSize()
                .focusRequester(focusRequester = focusRequester),
            value = pin,
            onValueChange = { novoValor ->
                if (novoValor.all { it.isDigit() } && novoValor.length <= 4)
                    onPinChange(novoValor)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            )
        )
    }
}


