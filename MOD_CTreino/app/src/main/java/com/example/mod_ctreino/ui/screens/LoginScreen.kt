package com.example.mod_ctreino.ui.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.mod_ctreino.R
import com.example.mod_ctreino.ui.components.PinField
import com.example.mod_ctreino.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
){

    val context = LocalContext.current

    var pin by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    LaunchedEffect(isError) {
        if (isError){
            delay(2000)
            isError = false
        }
    }

    Box(Modifier.fillMaxSize().imePadding(),
        contentAlignment = Alignment.Center) {


        Column(
            modifier = Modifier.size(340.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier.size(120.dp),
                contentDescription = null
            )
            Text("Aprender+", style = Typography.bodyMedium)



            PinField(
                pin = pin,
                isError = isError,
                onPinChange = { valor -> pin = valor }
            )

            Button(
                onClick = {
                    if (pin.length < 4)
                        Toast.makeText(
                            context,
                            "Todos os campos são obrigatórios",
                            Toast.LENGTH_SHORT
                        ).show()
                    else {
                        if (pin != "3245") {
                            isError = true
                            Toast.makeText(context, "Pin incorreto", Toast.LENGTH_SHORT).show()
                            pin = ""
                        } else {
                            onLoginSuccess()
                        }
                    }
                }
            ) { Text("Entrar") }
        }
    }
}
