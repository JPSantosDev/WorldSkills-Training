package com.example.tudo.ui.models.D1

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DadosDao(
    val cursos: List<Curso>,

    @SerialName("professores_id")
    val professores: List<Professor>,

    val categorias: List<Categoria>
)