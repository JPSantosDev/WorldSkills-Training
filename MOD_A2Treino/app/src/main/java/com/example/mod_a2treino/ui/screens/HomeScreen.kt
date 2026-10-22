package com.example.mod_a2treino.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mod_a2treino.ui.repository.ApiClient
import com.example.mod_a2treino.ui.repository.TokenManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {




    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var motd: String = ""




    LaunchedEffect(Unit) {
        motd = ApiClient.getMotd()
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
            ModalDrawerSheet() {

                Text("Menu", modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    modifier = Modifier.padding(12.dp),
                    label = {
                        Text("Lista de escolas")
                    },
                    selected = true,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    modifier = Modifier.padding(12.dp),
                    label = {
                        Text("Mapa")
                    },
                    selected = true,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )


                NavigationDrawerItem(
                    modifier = Modifier.padding(12.dp),
                    label = {
                        Text("Logout")
                    },
                    selected = true,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            TokenManager.clearToken()
                            onLogout()
                        }
                    }
                )

            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Bem vindo usuário") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    }
                )
            }
        ) { pad ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Tela inicial")
                Spacer(Modifier.height(12.dp))
                Text("Mensagem do dia: " +
                        "$motd")


                Button(onClick = {
                    scope.launch {
                        if (!ApiClient.validateToken("token_invalido_proposital").valid) {
                            onLogout()
                        }
                    }
                }) {
                    Text("Forçar token invalido")
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPre(){
    HomeScreen(
        onLogout = {}
    )
}