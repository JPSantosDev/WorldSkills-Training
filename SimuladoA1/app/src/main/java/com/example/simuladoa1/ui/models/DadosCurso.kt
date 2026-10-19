package com.example.simuladoa1.ui.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class DadosCurso (
    val id: Int,
    val peso: Int,
    val tipo: String,
    val enunciado: String,
    val alternativas: JsonElement,
    val resposta:JsonElement
)

@Serializable
data class ListCurso(
    val perguntas: List<DadosCurso>
)