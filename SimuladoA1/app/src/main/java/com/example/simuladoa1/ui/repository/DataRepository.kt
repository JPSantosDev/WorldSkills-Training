package com.example.simuladoa1.ui.repository

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.simuladoa1.ui.models.DadosCurso
import com.example.simuladoa1.ui.models.ListCurso
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object DataRepository {

    val jsonInstance = Json {
        ignoreUnknownKeys = true
    }

    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(jsonInstance)
        }
    }
    suspend fun loadJson(context: Context) = withContext(Dispatchers.IO){
        val url = context.assets.open("bancoQuestoes.json").bufferedReader().use { it.readText() }
        val jsonDecoded = jsonInstance.decodeFromString<ListCurso>(url)
        jsonDecoded.perguntas
    }

}
