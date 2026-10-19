package com.example.simuladod1.ui.repository

import android.content.Context
import com.example.simuladod1.ui.models.JsonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

object DataRepository {

    val jsonInstance = Json {
        ignoreUnknownKeys = true
    }

    suspend fun loadJson(context: Context) = withContext(Dispatchers.IO){
        val json = context.assets.open("dados.json").bufferedReader().use { it.readText()}
        jsonInstance.decodeFromString<JsonData>(json)
    }
}