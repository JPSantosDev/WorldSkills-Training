package com.example.mod_a2treino.ui.models

import kotlinx.serialization.Serializable
import java.io.File


@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String?
)

@Serializable
data class TokenValidationRequest(
    val token: String
)

@Serializable
data class TokenValidationResponse(
    val valid: Boolean,
    val payload: PayloadData
)

@Serializable
data class PayloadData(
    val sub: String,
    val email: String,
    val iat: Long,
    val exp: Long,
    val name: String
)

@Serializable
data class Motd(
    val message: String
)
@Serializable
data class SchoolListResponse(
    val total: Int,
    val escolas: List<School>
)
@Serializable
data class School(
    val id: Int,
    val nome: String,
    val avaliacao: Double,
    val image: String,
    val latitude: Double,
    val longitude: Double,
)


