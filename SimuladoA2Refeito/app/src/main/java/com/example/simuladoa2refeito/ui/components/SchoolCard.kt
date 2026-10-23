package com.example.simuladoa2refeito.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.simuladoa2refeito.ui.models.EscolaData

@Composable
fun SchoolCard(
    escola: EscolaData,
    onSchoolDetail: (Int) -> Unit
){
    Card(
        onClick = {
            onSchoolDetail(escola.id)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            Text(escola.nome)
            StarRating(escola.avaliacao.toFloat())
        }
    }
}