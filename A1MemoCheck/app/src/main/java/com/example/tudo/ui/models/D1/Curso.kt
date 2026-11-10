package com.example.tudo.ui.models.D1

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Curso(
    val id: Int,
    val nomeCompleto: String,
    val nomeBreve: String,

    @SerialName("categoria_id")
    val categoriaId: Int,

    val visivel: Boolean,
    val dataInicio: String,
    val dataFim: String,
    val descricao: String,
    val formato: String,

    @SerialName("professores_id")
    val professoresId: List<Int>,

    val porcentagem: Double
)