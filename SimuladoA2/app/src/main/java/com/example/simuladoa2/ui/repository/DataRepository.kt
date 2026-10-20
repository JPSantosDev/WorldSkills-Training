package com.example.simuladoa2.ui.repository

import android.content.Context
import com.example.simuladoa2.ui.models.GenerateTokenRequest
import com.example.simuladoa2.ui.models.GenerateTokenResponse
import com.example.simuladoa2.ui.models.ValidateTokenRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
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


    suspend fun loadJson(context: Context){

    }

    suspend fun generateToken(email: String, password: String) = withContext(Dispatchers.IO){
       val response = client.post {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/jwt/generate_token")
           setBody(
               GenerateTokenRequest(
                   email = email,
                   password = password
               )
           )
        }
        val email = response.body<GenerateTokenResponse>().token
        email
    }

    suspend fun validateToken() =withContext(Dispatchers.IO){

        val response = client.post {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/jwt/validate_token")
            setBody(ValidateTokenRequest())
        }



    }
}