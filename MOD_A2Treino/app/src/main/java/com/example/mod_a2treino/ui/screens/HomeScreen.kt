package com.example.mod_a2treino.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mod_a2treino.ui.repository.ApiClient
import com.example.mod_a2treino.ui.repository.TokenManager
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            ApiClient.validateToken(TokenManager.token ?: "")
        } catch (e: Exception) {
            Toast.makeText(context, "Token inválido ou expirado", Toast.LENGTH_SHORT).show()
            onLogout()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text("Tela inicial")

        Button(onClick = {
            scope.launch {
                if (!ApiClient.validateToken("token_invalido_proposital").valid){
                    onLogout()
                }
            }
        }) {
            Text("Forçar token invalido")
        }
    }
}