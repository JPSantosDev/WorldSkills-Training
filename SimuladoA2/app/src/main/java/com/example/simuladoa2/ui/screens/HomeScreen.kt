package com.example.simuladoa2.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.simuladoa2.R
import com.example.simuladoa2.ui.enums.CurrentScreen
import com.example.simuladoa2.ui.repository.DataRepository
import com.example.simuladoa2.ui.repository.TokenManager

@Composable
fun HomeScreen(
    context: Context,
    onSchoolList: () -> Unit,
    currentScreen: CurrentScreen,
    onMap: () -> Unit,
    onLogout: () -> Unit
){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var motd by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        loading = true
        motd = DataRepository.getMotd()

        if (DataRepository.validateToken(TokenManager.token).valid){

        }
        else{
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
                            HorizontalDivider()
                        }

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
                            label = { Text("Lista de Escolas") },
                            onClick = { onLogout() },
                            selected = false,
                        )
                    }
                }
            }
        ) {
            Scaffold() { pad ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(pad),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Bem-vindo ${TokenManager.user}")
                    Text(motd)
                }
            }
        }
    }
}