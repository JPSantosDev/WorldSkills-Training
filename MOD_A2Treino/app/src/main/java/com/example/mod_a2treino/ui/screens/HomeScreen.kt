package com.example.mod_a2treino.ui.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.mod_a2treino.R
import com.example.mod_a2treino.ui.enums.CurrentScreen
import com.example.mod_a2treino.ui.repository.ApiClient
import com.example.mod_a2treino.ui.repository.TokenManager

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    currentScreen: CurrentScreen,
    onMapa: () -> Unit,
    context: Context,
) {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    LaunchedEffect(Unit) {
        try {
            ApiClient.validateToken(TokenManager.token ?: "")
        } catch (e: Exception) {
            Toast.makeText(context, "Token inválido ou expirado", Toast.LENGTH_SHORT).show()
            onLogout()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        Icon(
                            painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = null
                        )
                        Text("Aprender +")
                    }
                    HorizontalDivider()

                    NavigationDrawerItem(
                        label = {Text("Lista das Escolas")},
                        onClick = {
                            onLogout()
                        },
                        selected = currentScreen == CurrentScreen.LIST_SCHOOL
                    )
                    NavigationDrawerItem(
                        label = {Text("Mapa")},
                        onClick = {
                            onMapa()
                        },
                        selected = currentScreen == CurrentScreen.MAPA
                    )
                    NavigationDrawerItem(
                        label = {Text("Logout")},
                        onClick = {
                            onLogout()
                        },
                        selected = false
                    )

                }
            }
        },

        ) {
        Scaffold() { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = paddingValues)
            ) {


            }
        }
    }
}