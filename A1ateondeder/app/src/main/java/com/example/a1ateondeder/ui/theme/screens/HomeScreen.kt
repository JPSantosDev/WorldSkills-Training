package com.example.a1ateondeder.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a1ateondeder.ui.theme.enums.CurrentScreen
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currentScreen: CurrentScreen,
    onHome: () -> Unit,
    onQuiz: () -> Unit,
    onGenius: () -> Unit,
    onMemoCheck: () -> Unit
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
            ) {
                Column(Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Icon(Icons.Default.AccountCircle,contentDescription = null,modifier = Modifier.size(46.dp))
                    HorizontalDivider(modifier = Modifier.padding(8.dp))

                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("Home") },
                        selected = currentScreen == CurrentScreen.HOME,
                        onClick = onHome,
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("QuizMyBrain") },
                        selected = currentScreen == CurrentScreen.QUIZMYBRAIN,
                        onClick = onQuiz,
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("GeniusPLay") },
                        selected = currentScreen == CurrentScreen.GENIUSPLAY,
                        onClick = onGenius,
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("MemoCheck") },
                        selected = currentScreen == CurrentScreen.MEMOCHECK,
                        onClick = onMemoCheck,
                    )
                    NavigationDrawerItem(
                        modifier = Modifier.padding(8.dp),
                        label = { Text("Sair") },
                        selected = false,
                        onClick = {
                            exitProcess(200)
                        },
                    )
                }
            }
        },
        drawerState = drawerState,
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
                                Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { pad ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(pad)
            ) {

            }
        }
    }
}