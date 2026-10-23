package com.example.simuladoa2refeito.ui.models

import kotlinx.serialization.Serializable

@Serializable
data class Comentario(
    val id:Int,
    val id_escola: Int,
    val comentario: String,
    val resposta: Int?,
    val autor_id: Int,
    val autor_nome: String,
    val data_hora: String,
    val respostas: List<Int?>
)