package com.example.mod_ctreino.ui.repository


import android.content.Context
import com.example.mod_ctreino.ui.model.DadosDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class DataRepository(val context: Context){

    val json = Json {
        ignoreUnknownKeys = true
    }
    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(json)
        }
    }

    suspend fun loadJson() = withContext(Dispatchers.IO) {
        val url = context.assets.open("artigos.json").bufferedReader().use { it.readText() }
        val jsonDecoded = json.decodeFromString<List<DadosDto>>(url)
        jsonDecoded

    }
}
