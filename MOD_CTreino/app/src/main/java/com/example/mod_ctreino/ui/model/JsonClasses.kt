package com.example.mod_ctreino.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class DadosDto(
    val id: Int,
    val titulo: String,
    val descricao: String,
    val data: String
)