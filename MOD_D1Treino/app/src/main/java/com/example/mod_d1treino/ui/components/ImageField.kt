package com.example.mod_d1treino.ui.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ImageField(
    uri: Uri?,
    onUriChange: (Uri) -> Unit
){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scale = remember(uri) { Animatable(1f) }
    val offsetX = remember(uri) { Animatable(0f) }
    val offsetY = remember(uri) { Animatable(0f) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { escolhida->
        if (escolhida != null) onUriChange(escolhida)
    }

    val bitmap by produceState<ImageBitmap?>(initialValue = null,uri) {
        value = uri?.let {
            withContext(Dispatchers.IO){
                runCatching {
                    context.contentResolver.openInputStream(it)?.use { stream ->
                        BitmapFactory.decodeStream(stream)?.asImageBitmap()
                    }
                }.getOrNull()
            }
        }
    }


    Column() {
        Text("Imagem", style = MaterialTheme.typography.labelMedium )
        Spacer(Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .border(1.dp,Color.Black)
                .clickable(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                )
                .clipToBounds()
                .pointerInput(uri){
                    awaitEachGesture {
                        awaitFirstDown(requireUnconsumed = false)
                        do {
                            val event = awaitPointerEvent()
                            val zoom = event.calculateZoom()
                            val pan = event.calculatePan()
                            scope.launch {
                                scale.snapTo(
                                    (scale.value*zoom).coerceIn(1f,5f)
                                )
                                offsetX.snapTo(offsetX.value + pan.x)
                                offsetY.snapTo(offsetY.value + pan.y)
                            }
                        } while (event.changes.any { it.pressed })

                        scope.launch { scale.animateTo(1f) }
                        scope.launch { offsetX.animateTo(0f) }
                        scope.launch { offsetY.animateTo(0f) }
                    }
                }
                .graphicsLayer{
                    scaleX = scale.value
                    scaleY = scale.value
                    translationX = offsetX.value
                    translationY = offsetY.value
                },
            contentAlignment = Alignment.Center
        ){
            bitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}