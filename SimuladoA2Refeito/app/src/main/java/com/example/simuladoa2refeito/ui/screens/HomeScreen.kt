package com.example.simuladoa2refeito.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.simuladoa2refeito.R
import com.example.simuladoa2refeito.ui.enums.CurrentScreen
import com.example.simuladoa2refeito.ui.repository.DataRepository
import com.example.simuladoa2refeito.ui.repository.TokenManager
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    context: Context,
    onSchoolList: () -> Unit,
    currentScreen: CurrentScreen,
    onMap: () -> Unit,
    onLogout: () -> Unit
){

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var motd by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        loading = true
        motd = DataRepository.getMotd()

        if (!DataRepository.validateToken(TokenManager.token).valid){
            Toast.makeText(context,"Token inválido, faça login novamente", Toast.LENGTH_LONG).show()
            onLogout()
        }
        loading = false
    }

    if (loading){
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    } else {


        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_launcher_foreground),
                                contentDescription = null
                            )
                            Text("Aprender +")
                        }

                        HorizontalDivider()

                        NavigationDrawerItem(
                            label = { Text("Lista de Escolas") },
                            onClick = { onSchoolList() },
                            selected = currentScreen == CurrentScreen.LIST_ESCOLA,
                        )
                        NavigationDrawerItem(
                            label = { Text("Mapa") },
                            onClick = { onMap() },
                            selected = currentScreen == CurrentScreen.MAP,
                        )
                        NavigationDrawerItem(
                            label = { Text("Logout") },
                            onClick = { onLogout() },
                            selected = false,
                        )
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Menu,contentDescription = null)
                            }
                        }
                    )
                }
            ) { pad ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(pad),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Bem-vindo ${TokenManager.user}")
                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(motd)
                    }
                }
            }
        }
    }
}