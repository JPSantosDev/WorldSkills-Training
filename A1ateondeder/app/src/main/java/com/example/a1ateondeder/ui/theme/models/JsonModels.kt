package com.example.a1ateondeder.ui.theme.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class JsonModels(
    val perguntas: List<Pergunta>
)


@Serializable
data class Pergunta(
    val id:Int,
    val peso: Int,
    val tipo: String,
    val enunciado: String,
    val alternativas: JsonElement,
    val resposta: JsonElement
)
