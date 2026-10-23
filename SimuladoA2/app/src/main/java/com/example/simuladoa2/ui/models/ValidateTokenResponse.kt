package com.example.simuladoa2.ui.models

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTokenResponse (
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