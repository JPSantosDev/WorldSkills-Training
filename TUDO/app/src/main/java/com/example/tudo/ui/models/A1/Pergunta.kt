package com.example.tudo.ui.models.A1

import kotlinx.serialization.json.JsonElement

data class Pergunta(
    val id: Int,
    val peso:Int,
    val tipo: String,
    val enunciado: String,
    val alternativas: JsonElement,
    val resposta: JsonElement
)
