package com.example.mod_d1treino.ui.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DadosDao(
    val cursos: List<Curso>,

    @SerialName("professores_id")
    val professores: List<Professor>,

    val categorias: List<Categoria>
)

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

@Serializable
data class Professor(
    val id: Int,
    val nome: String,
    val email: String,
    val telefone: String,
    val descricao: String
)

@Serializable
data class Categoria(
    val id: Int,
    val nome: String
)