package com.example.mod_a2treino.ui.repository

import com.example.mod_a2treino.ui.models.LoginRequest
import com.example.mod_a2treino.ui.models.LoginResponse
import com.example.mod_a2treino.ui.models.Motd
import com.example.mod_a2treino.ui.models.SchoolListResponse
import com.example.mod_a2treino.ui.models.TokenValidationRequest
import com.example.mod_a2treino.ui.models.TokenValidationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json




private const val BASE_URL = "https://workspace.dinizeotecnologia.com.br/worldskills"
private const val JWT_URL = "$BASE_URL/jwt"
private const val A2_URL = "$BASE_URL/A2"
object ApiClient{
    val jsonInstance = Json {
        ignoreUnknownKeys = true
    }

    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(jsonInstance)
        }
    }



    suspend fun apiLogin(email: String, password: String): String = withContext(Dispatchers.IO) {
        val response = ApiClient.client.post {
            url("$JWT_URL/generate_token")
            setBody(LoginRequest(email, password))
        }

        if (!response.status.isSuccess()) {
            throw IllegalStateException("Erro ao autenticar. Código: ${response.status.value}")
        }

        val token = response.body<LoginResponse>().token?.takeIf { it.isNotBlank() }

        token ?: throw Exception("Token ausente")
    }

    suspend fun invalidToken(token: String = ""): TokenValidationResponse = withContext(Dispatchers.IO){
        val response = ApiClient.client.post {
            url("$JWT_URL/validate_token")
            setBody(TokenValidationRequest(token))
        }
        if (!response.status.isSuccess()) {
            throw IllegalStateException("Erro ao validar token. Código: ${response.status.value}")
        }
        val validationResponse = response.body<TokenValidationResponse>().valid
        val payload = response.body<TokenValidationResponse>().payload

        return@withContext TokenValidationResponse(
            valid = validationResponse,
            payload = payload
        )
    }
    suspend fun validateToken(token: String): TokenValidationResponse = withContext(Dispatchers.IO) {
        val response = ApiClient.client.post {
            url("$JWT_URL/validate_token")
            setBody(TokenValidationRequest(token))
        }
        if (!response.status.isSuccess()) {
            throw IllegalStateException("Erro ao validar token. Código: ${response.status.value}")
        }
        val validationResponse = response.body<TokenValidationResponse>().valid
        val payload = response.body<TokenValidationResponse>().payload

        return@withContext TokenValidationResponse(
            valid = validationResponse,
            payload = payload
        )
    }

    suspend fun getMotd(): String = withContext(Dispatchers.IO) {
        val response = ApiClient.client.get {
            url("$A2_URL/motd")
            setBody(Motd)
        }
        response.body<Motd>().message
    }

    suspend fun getSchoolReactions(id_escola: Int): List<String> = withContext(Dispatchers.IO) {
        val response = ApiClient.client.get {
            url("$A2_URL/reactions/$id_escola")
        }
        response.body<List<String>>()
    }

    suspend fun getSchoolList(): SchoolListResponse = withContext(Dispatchers.IO){
        val response = ApiClient.client.get {
            url("$A2_URL/school_list")
            setBody(SchoolListResponse)
        }

        response.body()

    }
}

