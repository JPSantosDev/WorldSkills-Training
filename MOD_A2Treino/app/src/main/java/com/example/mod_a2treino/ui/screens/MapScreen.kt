package com.example.mod_a2treino.ui.screens

import android.content.Context
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.example.mod_a2treino.ui.repository.ApiClient
import com.example.mod_a2treino.ui.repository.TokenManager

@Composable
fun MapScreen(
    context: Context,
    onLogout: () -> Unit
){

    LaunchedEffect (Unit) {
        try {
            ApiClient.validateToken(TokenManager.token ?: "")
        } catch (e: Exception) {
            Toast.makeText(context, "Token inválido ou expirado", Toast.LENGTH_SHORT).show()
            onLogout()
        }
    }


}