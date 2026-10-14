package com.example.mod_a2treino.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mod_a2treino.ui.components.ErroPopup
import com.example.mod_a2treino.ui.components.SchoolCard
import com.example.mod_a2treino.ui.enums.CurrentScreen
import com.example.mod_a2treino.ui.models.EscolasData
import com.example.mod_a2treino.ui.repository.ApiManager
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListEscolaScreen(
    onMapa: () -> Unit,
    onLogout: () -> Unit,
    onListEscola: () -> Unit,
    onCardClick: (Int) -> Unit,
    onBack: () -> Unit,
) {

    val escolas = remember { mutableStateListOf<EscolasData>() }
    var loading by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var currentScreen = CurrentScreen.LIST_ESCOLA
    val scope = rememberCoroutineScope()
    var erro by remember { mutableStateOf("") }
    var tentativa by remember { mutableIntStateOf(0) }


    LaunchedEffect(Unit, tentativa) {
        loading = true
        try {
            val escolasData = ApiManager.getSchoolList().escolas
            escolas.clear()
            escolas.addAll(escolasData)
        } catch (e: Exception){
            erro = e.message ?: "Não foi possível concluir a operação, tente novamente"
        } finally {
            loading = false
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row() {
                    Text("Aprender+")
                }
                HorizontalDivider()

                NavigationDrawerItem(
                    label = { Text("Mapa") },
                    selected = currentScreen == CurrentScreen.MAPA,
                    onClick = onMapa
                )
                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    onClick = onLogout
                )
                NavigationDrawerItem(
                    label = { Text("Lista de Escolas") },
                    selected = currentScreen == CurrentScreen.LIST_ESCOLA,
                    onClick = onListEscola
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Lista de Escolas") },
                    navigationIcon = {
                        Row() {
                            IconButton(
                                onClick = onBack
                            ) { Icon(Icons.Default.ArrowBack, contentDescription = null) }

                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) { Icon(Icons.Default.Menu, contentDescription = null) }
                        }
                    },
                )
            }
        ) { pad ->
            Column(
                modifier = Modifier
                    .padding(pad)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loading) CircularProgressIndicator()
                else if (erro.isNotBlank()){
                    ErroPopup(
                        error = erro,
                        tentativa = tentativa,
                        dismiss = {erro = ""},
                        retry = { tentativa++ },
                    )
                }
                else{
                    val collator = Collator.getInstance(Locale("pt","BR"))
                    val escolasOrdenadas = escolas.sortedWith { escola1, escola2 -> collator.compare(escola1.nome,escola2.nome)  }


                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(escolasOrdenadas,key = {it.id}){ escola->
                            SchoolCard(escola, onCardClick = onCardClick)
                        }
                    }
                }
            }
        }
    }
}