package com.example.mod_ctreino.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import io.ktor.util.collections.StringMap

@Composable
fun VideoArtigo(
    nomeVideo: String,
    modifier:Modifier = Modifier
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var posicaoSalva by rememberSaveable(nomeVideo) {
        mutableLongStateOf(0L)
    }
    var estavaReproduzindo by rememberSaveable(nomeVideo) {
        mutableStateOf(false)
    }
    var isPlaying by remember {
        mutableStateOf(false)
    }

    val nomeRecurso = nomeVideo
        .substringBeforeLast(".")
        .lowercase()

    val resourceId = remember(nomeRecurso){
        context.resources.getIdentifier(nomeRecurso,"raw",context.packageName)
    }

    if (resourceId == 0) {
        Text("Vídeo não encontrado: $nomeVideo")
        return
    }



}