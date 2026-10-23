package com.example.simuladoa2.ui.repository

import android.content.Context
import com.example.simuladoa2.ui.models.GenerateTokenRequest
import com.example.simuladoa2.ui.models.GenerateTokenResponse
import com.example.simuladoa2.ui.models.MOTD
import com.example.simuladoa2.ui.models.SchoolListResponse
import com.example.simuladoa2.ui.models.ValidateTokenRequest
import com.example.simuladoa2.ui.models.ValidateTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

object DataRepository {
    val jsonInstance = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        explicitNulls = false
    }
    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(jsonInstance)
        }
    }


    suspend fun generateToken(email: String, password: String) =
        withContext(Dispatchers.IO) {
            val response = client.post(
            ) {
                url("https://workspace.dinizeotecnologia.com.br/worldskills/jwt/generate_token")
                setBody(
                    GenerateTokenRequest(
                        email = email,
                        password = password
                    )
                )
                contentType(ContentType.Application.Json)
            }

            val token = response.body<GenerateTokenResponse>().token
            token
        }

    suspend fun validateToken(token: String) =withContext(Dispatchers.IO) {
        val response = client.post {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/jwt/validate_token")
            setBody(
                ValidateTokenRequest(
                    token = token
                )
            )
            contentType(ContentType.Application.Json)
        }

        val valid = response.body<ValidateTokenResponse>().valid
        val payload = response.body<ValidateTokenResponse>().payload

        return@withContext ValidateTokenResponse(
            valid = valid,
            payload = payload
        )
    }


    suspend fun getMotd() = withContext(Dispatchers.IO) {
        try {
            val response = client.get(
                "https://workspace.dinizeotecnologia.com.br/worldskills/A2/motd"
            )

            val resposta = response.bodyAsText()

            println("Status MOTD: ${response.status}")
            println("JSON MOTD: $resposta")

            if (response.status.isSuccess()) {
                Json.decodeFromString<MOTD>(resposta).MOTD
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    suspend fun getSchoolList() = withContext(Dispatchers.IO){
        val response = client.get {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/A2/school_list")
        }

        val dados = response.body<SchoolListResponse>()
        return@withContext SchoolListResponse(
            total = dados.total,
            escolas = dados.escolas
        )
    }
}