package com.example.simuladoa2.ui.models

import io.ktor.util.collections.StringMap
import kotlinx.serialization.Serializable
import java.io.File
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
    val latitude: Double,
    val longitude: Double
)
