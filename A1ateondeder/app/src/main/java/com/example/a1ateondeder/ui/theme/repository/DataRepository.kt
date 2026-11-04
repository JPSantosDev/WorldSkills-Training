package com.example.a1ateondeder.ui.theme.repository

import android.content.Context
import com.example.a1ateondeder.ui.theme.models.JsonModels
import com.example.a1ateondeder.ui.theme.models.Pergunta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

object DataRepository {
    val jsonInstance = Json {
        ignoreUnknownKeys = true
    }
    suspend fun loadJson(context: Context) = withContext(Dispatchers.IO){
        val json = context.assets.open("bancoQuestoes.json").bufferedReader().use { it.readText() }
        jsonInstance.decodeFromString<JsonModels>(json)
    }
}