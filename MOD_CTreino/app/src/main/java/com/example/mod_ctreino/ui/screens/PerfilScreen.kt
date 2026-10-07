package com.example.mod_ctreino.ui.screens

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.example.mod_ctreino.ui.components.PerfilArea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    uri: Uri?,
    onUriChange: (Uri) -> Unit
) {


    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { escolhida ->
        if (escolhida != null) onUriChange(escolhida)
    }

    val bitmap by produceState<ImageBitmap?>(null, key1 = uri) {
        value = uri?.let {
            withContext(Dispatchers.IO) {
                runCatching {
                    context.contentResolver.openInputStream(it)?.use { stream ->
                        BitmapFactory.decodeStream(stream)?.asImageBitmap()
                    }
                }.getOrNull()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },

                )
        }
    ) { pad->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pad)
        ) {
            PerfilArea(
                bitmap = bitmap,
                onClick = {
                    launcher.launch(
                    PickVisualMediaRequest( ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }
    }
}