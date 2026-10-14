package com.example.mod_a2treino.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import androidx.media3.effect.Crop
import com.example.mod_a2treino.ui.components.AvaliacaoSlider
import com.example.mod_a2treino.ui.components.ComentarioCard
import com.example.mod_a2treino.ui.models.Comment
import com.example.mod_a2treino.ui.models.CommentRequest
import com.example.mod_a2treino.ui.models.EscolasData
import com.example.mod_a2treino.ui.repository.ApiManager
import com.example.mod_a2treino.ui.repository.TokenManager
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheEscolaScreen(
    escola: EscolasData,
    onBack: () -> Unit,
    onMapa: () -> Unit
) {
    val comentarios = remember { mutableStateListOf<Comment>() }
    var texto by remember { mutableStateOf("") }
    var enviando by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(escola.id) {
        try {
            val lista = ApiManager.getComments()
            comentarios.clear()
            comentarios.addAll(lista)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("DetalheEscola", "Erro ao carregar comentários", e)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(escola.nome) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { pad ->
        LazyColumn(
            modifier = Modifier.padding(pad).padding(horizontal = 8.dp).imePadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { AvaliacaoSlider(escola.avaliacao.toFloat()) }
            item {
                Button(onClick = onMapa, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Map, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Mapa")
                }
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Comentários", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = texto,
                        onValueChange = { texto = it },
                        placeholder = { Text("Escreva um comentário...") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        enabled = texto.isNotBlank() && !enviando,
                        onClick = {
                            scope.launch {
                                enviando = true
                                try {
                                    ApiManager.postComment(
                                        id = escola.id,
                                        comentario = texto.trim(),
                                        resposta = null,
                                        autor = "a"
                                    )
                                    texto = ""
                                    val lista = ApiManager.getComments()
                                    comentarios.clear()
                                    comentarios.addAll(lista)
                                } catch (e: CancellationException) {
                                    throw e
                                } catch (e: Exception) {
                                    Log.e("DetalheEscola", "Erro ao comentar", e)
                                } finally {
                                    enviando = false
                                }
                            }
                        }
                    ) { Text("Comentar") }
                }
            }
            items(comentarios, key = { it.id }) { c ->
                ComentarioCard(c)
            }
        }
    }
}
