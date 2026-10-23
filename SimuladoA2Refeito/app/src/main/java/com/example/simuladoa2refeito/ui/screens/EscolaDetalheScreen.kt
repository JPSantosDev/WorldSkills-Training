package com.example.simuladoa2refeito.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simuladoa2refeito.ui.components.ReactSlider
import com.example.simuladoa2refeito.ui.models.Comentario
import com.example.simuladoa2refeito.ui.models.EscolaData
import com.example.simuladoa2refeito.ui.repository.DataRepository
import com.example.simuladoa2refeito.ui.repository.TokenManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EscolaScreenDetail(
    escolas: List<EscolaData>,
    id: Int,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    context: Context
){
    var loading by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        loading = true
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
    } else

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text("Detalhes Escola")},
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBack()
                            }
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    Row() {
                        Button(onClick = {}) {
                            Text("Fazer Comentário")
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = {}) {
                            Text("MAPA")
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                Text(escolas[id].nome)
                ReactSlider((escolas[id].avaliacao).toFloat())

            }
        }
}