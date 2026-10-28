package com.example.mod_a2treino.ui.device

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ScreenshotObserver(private val context: Context) {
    val novosScreenshots: Flow<Uri> = callbackFlow {
        val inicio = System.currentTimeMillis() / 1000 - 1

        val observer = object : ContentObserver(Handler(Looper.getMainLooper())){
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
            }
        }
        context.contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            observer
        )
        awaitClose { context.contentResolver.unregisterContentObserver(observer) }
    }
}