package com.example.simuladoa2.ui.models

import kotlinx.serialization.Serializable

@Serializable

data class ValidateTokenRequest (
    val token: String
)