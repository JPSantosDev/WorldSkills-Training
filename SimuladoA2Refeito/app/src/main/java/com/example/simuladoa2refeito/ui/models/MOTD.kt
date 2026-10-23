package com.example.simuladoa2refeito.ui.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MOTD(
    @SerialName("MOTD")
    val motd:  String
)
