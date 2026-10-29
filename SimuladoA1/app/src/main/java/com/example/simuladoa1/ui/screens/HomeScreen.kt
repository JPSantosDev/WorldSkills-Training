package com.example.simuladoa1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.simuladoa1.R
import com.example.simuladoa1.ui.enums.CurrentScreen
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currentScreen: CurrentScreen,
    onHome: () -> Unit,
    onQuizMyBrain: () -> Unit,
    onGeniusPlay: () -> Unit,
    onMemoCheck: () -> Unit
) {

    LaunchedEffect(Unit) {

    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        modifier = Modifier.size(96.dp).padding(8.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    NavigationDrawerItem(
                        label = { Text("Home") },
                        selected = currentScreen == CurrentScreen.HOME,
                        onClick = {
                            onHome()
                        },
                    )

                    NavigationDrawerItem(
                        label = { Text("QuizMyBrain") },
                        selected = currentScreen == CurrentScreen.QUIZMYBRAIN,
                        onClick = {
                            onQuizMyBrain()
                        },
                    )

                    NavigationDrawerItem(
                        label = { Text("GeniusPlay") },
                        selected = currentScreen == CurrentScreen.GENIUSPLAY,
                        onClick = {
                            onGeniusPlay()
                        },
                    )

                    NavigationDrawerItem(
                        label = { Text("MemoCheck") },
                        selected = currentScreen == CurrentScreen.MEMOCHECK,
                        onClick = {
                            onMemoCheck()
                        },
                    )

                    NavigationDrawerItem(
                        label = { Text("Sair") },
                        selected = false,
                        onClick = { exitProcess(0) },
                    )
                }
            }
        },
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
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    }
                )
            },

            ) { pad ->

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(pad)
                    .fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                Text("Bem-vindo ao Aprender+")
            }
        }
    }
}