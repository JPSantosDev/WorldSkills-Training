package com.example.simuladoa2refeito.ui.models

import kotlinx.serialization.Serializable


@Serializable
data class SchoolListResponse(
    val total: Int,
    val escolas: List<EscolaData>
)
@Serializable
data class EscolaData(
    val id: Int,
    val nome: String,
    val avaliacao: Double,
    val image: String,
    val latitude: Double,
    val longitude: Double
)
