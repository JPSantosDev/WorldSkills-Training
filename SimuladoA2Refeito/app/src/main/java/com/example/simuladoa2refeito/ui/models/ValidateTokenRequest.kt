package com.example.simuladoa2refeito.ui.models

import kotlinx.serialization.Serializable

@Serializable

data class ValidateTokenRequest (
    val token: String
)