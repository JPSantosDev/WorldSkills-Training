package com.example.mod_d1treino.ui.repository

import android.content.Context
import com.example.mod_d1treino.ui.models.DadosDao

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class DataRepository (private val context: Context){

    val jsonInstance = Json {
        ignoreUnknownKeys = true
    }

    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(jsonInstance)
        }
    }

    suspend fun loadCursos() = withContext(Dispatchers.IO){
        val dados = context.assets.open("dados.json").bufferedReader().use { it.readText() }
        val decodedDados = jsonInstance.decodeFromString<DadosDao>(dados)
        decodedDados.cursos

    }
    suspend fun loadCategorias() = withContext(Dispatchers.IO){
        val dados = context.assets.open("dados.json").bufferedReader().use { it.readText() }
        val decodedDados = jsonInstance.decodeFromString<DadosDao>(dados)
        decodedDados.categorias

    }
    suspend fun loadProfessores() = withContext(Dispatchers.IO){
        val dados = context.assets.open("dados.json").bufferedReader().use { it.readText() }
        val decodedDados = jsonInstance.decodeFromString<DadosDao>(dados)
        decodedDados.professores
    }

}