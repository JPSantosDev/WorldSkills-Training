package com.example.tudo.ui.models.D1

import kotlinx.serialization.Serializable

@Serializable
data class Professor(
    val id: Int,
    val nome: String,
    val email: String,
    val telefone: String,
    val descricao: String
)