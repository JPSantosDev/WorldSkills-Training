package com.example.simuladoa2refeito.ui.models

import kotlinx.serialization.Serializable

@Serializable
data class GenerateTokenRequest(
    val email: String,
    val password: String
)
