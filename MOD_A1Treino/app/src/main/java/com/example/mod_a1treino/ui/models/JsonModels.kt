package com.example.mod_a1treino.ui.models

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class ListDadosDto(
    val perguntas: List<DadosDto>
)
@Serializable
data class DadosDto(
    val id: Int,
    val peso: Int,
    val tipo: String,
    val enunciado: String,
    val alternativas: JsonElement,
    val resposta: JsonElement
)
@Serializable
data class PerguntaMeVf(
    val id: Int,
    val peso: Int,
    val tipo: String,
    val enunciado: String,
    val alternativas: List<String>,
    val resposta: Int
)
@Serializable
data class PerguntaRel(
    val id: Int,
    val peso: Int,
    val tipo: String,
    val enunciado: String,
    val alternativas: List<List<String>>,
    val resposta: List<List<Int>>
)
