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
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun PerfilArea(
    uri: Uri?,
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
            if (uri == null){
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            }
            else{
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = uri,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(28.dp)
                .background(Color.Gray,CircleShape)
                .clickable(onClick = onClick)
        ){
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null, tint = Color.White
            )
        }
    }
}