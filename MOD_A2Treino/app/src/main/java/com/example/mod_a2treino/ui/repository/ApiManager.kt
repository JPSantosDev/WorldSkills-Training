package com.example.mod_a2treino.ui.repository


import com.example.mod_a2treino.ui.models.CommentsResponse
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
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File

object ApiManager {

    private const val BASE_URL = "https://workspace.dinizeotecnologia.com.br/worldskills"
    private const val JWT_URL = "$BASE_URL/jwt"
    private const val A2_URL = "$BASE_URL/A2"

    val json = Json {
        ignoreUnknownKeys = true
    }

    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(json)
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }


    suspend fun apiLogin(email: String,password: String) = withContext(Dispatchers.IO) {
        val response = client.post {
            url("$JWT_URL/generate_token")
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email, password))
        }
        if (!response.status.isSuccess()){
            throw IllegalStateException("Erro ao autenticar. Código: ${response.status.value}")
        }
        val token = response.body<LoginResponse>().token
        token
    }


    suspend fun validateToken(token: String) = withContext(Dispatchers.IO) {
        val response = client.post {
            url("$JWT_URL/validate_token")
            contentType(ContentType.Application.Json)
            setBody(TokenValidationRequest(token))
        }
        if (!response.status.isSuccess()){
            throw IllegalStateException("Erro ao autenticar. Código: ${response.status.value}")
        }
        val payload = response.body<TokenValidationResponse>().payload
        val valid = response.body<TokenValidationResponse>().valid

        return@withContext TokenValidationResponse(
            valid,
            payload
        )
    }

    suspend fun getMotd() = withContext(Dispatchers.IO){
        val response = client.get {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/A2/motd")
        }
        val motd = response.body<Motd>().message
        motd
    }

    suspend fun getSchoolList() =withContext(Dispatchers.IO){
        val response = client.get {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/A2/school_list")
        }
        val total = response.body<SchoolListResponse>().total
        val escolas = response.body<SchoolListResponse>().escolas

        return@withContext SchoolListResponse(
            total,
            escolas
        )
    }
    suspend fun getComments() = withContext(Dispatchers.IO){
        val response = client.get {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/A2/comments")
        }
        val comentarios = response.body<CommentsResponse>().comments
        comentarios
    }

    suspend fun postComment(id: Int, comentario: String,resposta: Int?,autor: String) = withContext(Dispatchers.IO){
        val response = client.post {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/A2/comments")
            contentType(ContentType.Application.Json)
            setBody(CommentsResponse)
        }
    }



}