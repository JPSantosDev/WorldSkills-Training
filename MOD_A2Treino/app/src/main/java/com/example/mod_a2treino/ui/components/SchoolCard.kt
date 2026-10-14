package com.example.mod_a2treino.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mod_a2treino.ui.models.EscolasData

@Composable
fun SchoolCard(
    escola: EscolasData,
    onCardClick: (Int) -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onCardClick(escola.id) }
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(escola.nome)
            StarProgress(escola.avaliacao)
        }
    }
}

@Preview
@Composable
fun PreviewSchool(){
    SchoolCard(
        onCardClick = {},
        escola = EscolasData(
            id = 1,
            nome = "jean",
            avaliacao = 8.0,
            image = "",
            latitude = 123856.5,
            longitude = 123.1
        )
    )
}