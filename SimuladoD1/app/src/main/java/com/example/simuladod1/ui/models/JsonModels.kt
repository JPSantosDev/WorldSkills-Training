package com.example.simuladod1.ui.models

import kotlinx.serialization.Serializable


@Serializable
data class JsonData(
    val cursos: List<Curso>,
    val professores_id: List<Professor>,
    val categorias : List<Categoria>
)

@Serializable
data class Curso (
    val id: Int,
    val nomeCompleto:String,
    val nomeBreve: String,
    val categoria_id: Int,
    val visivel: Boolean,
    val dataInicio: String,
    val dataFim: String,
    val descricao: String,
    val formato: String,
    val professores_id: List<Int>,
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