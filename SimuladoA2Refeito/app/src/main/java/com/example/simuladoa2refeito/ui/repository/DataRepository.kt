package com.example.simuladoa2refeito.ui.repository

import com.example.simuladoa2refeito.ui.models.Comentario
import com.example.simuladoa2refeito.ui.models.GenerateTokenRequest
import com.example.simuladoa2refeito.ui.models.GenerateTokenResponse
import com.example.simuladoa2refeito.ui.models.MOTD
import com.example.simuladoa2refeito.ui.models.SchoolListResponse
import com.example.simuladoa2refeito.ui.models.ValidateTokenRequest
import com.example.simuladoa2refeito.ui.models.ValidateTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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

        val response = client.get {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/A2/motd")
            contentType(ContentType.Application.Json)
        }
        val dados = response.body<MOTD>().motd
        dados
    }

    suspend fun getSchoolList() = withContext(Dispatchers.IO){
        val response = client.get {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/A2/school_list")
            contentType(ContentType.Application.Json)
        }

        val dados = response.body<SchoolListResponse>()
        return@withContext SchoolListResponse(
            total = dados.total,
            escolas = dados.escolas
        )
    }
    suspend fun getReactions() = withContext(Dispatchers.IO){
        val response = client.get {
            url("https://workspace.dinizeotecnologia.com.br/worldskills/A2/comments")
            contentType(ContentType.Application.Json)
        }
        val dados = response.body<Comentario>()
        return@withContext Comentario(
            id = dados.id,
            id_escola = dados.id_escola,
            comentario = dados.comentario,
            resposta = dados.resposta,
            autor_id = dados.autor_id,
            autor_nome = dados.autor_nome,
            data_hora = dados.data_hora,
            respostas = dados.respostas
        )
    }
}