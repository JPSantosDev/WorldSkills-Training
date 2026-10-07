package com.example.mod_ctreino.ui.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PerfilArea(
    bitmap: ImageBitmap?,
    onClick: () -> Unit
) {

    Box(modifier = Modifier.size(96.dp)){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color.DarkGray)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ){
            bitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.fillMaxSize())
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(28.dp)
                .background(Color.Gray,CircleShape)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ){
            Icon(Icons.Default.CameraAlt,contentDescription = null, tint = Color.White)
        }

    }
}