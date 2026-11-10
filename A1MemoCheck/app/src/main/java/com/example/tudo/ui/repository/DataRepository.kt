package com.example.tudo.ui.repository

import android.content.Context
import com.example.tudo.ui.models.A1.Perguntas
import com.example.tudo.ui.models.C1.Artigo
import com.example.tudo.ui.models.C1.ArtigoDetail
import com.example.tudo.ui.models.D1.DadosDao
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun loadJsonA1(context: Context) = withContext(Dispatchers.IO){
        val json = context.assets.open("A1bancoQuestoes.json").bufferedReader().use { it.readText() }
        val decoded = jsonInstance.decodeFromString<Perguntas>(json)
        decoded.perguntas
    }

    suspend fun loadJsonC1(context: Context) = withContext(Dispatchers.IO){
        val json = context.assets.open("C1artigos.json").bufferedReader().use { it.readText() }
        val decoded = jsonInstance.decodeFromString<List<Artigo>>(json)
        decoded
    }

    suspend fun loadJsonC1Detalhado(context: Context) = withContext(Dispatchers.IO){
        val json = context.assets.open("C1artigo_2_detalhado.json").bufferedReader().use { it.readText() }
        val decoded = jsonInstance.decodeFromString<ArtigoDetail>(json)
        decoded
    }

    suspend fun loadJsonD1(context: Context) = withContext(Dispatchers.IO){
        val json = context.assets.open("C1artigo_2_detalhado.json").bufferedReader().use { it.readText() }
        val decoded = jsonInstance.decodeFromString<DadosDao>(json)
        decoded


    }


}