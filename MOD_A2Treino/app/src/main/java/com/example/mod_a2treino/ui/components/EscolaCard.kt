package com.example.mod_a2treino.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mod_a2treino.ui.models.School

@Composable
fun EscolaCard(
    escola: School,
    onClickSchool: (Int) -> Unit
){
    Card(
        onClick = { onClickSchool(escola.id) },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(escola.nome)
            Spacer(modifier = Modifier.weight(1f))
            StarRating(escola.avaliacao.toFloat())
        }
    }
}


@Preview
@Composable
fun EscolaCardPre(){
    EscolaCard(

        escola = School(
            id = 1,
            nome = "jean",
            avaliacao = 3.4,
            image = "a",
            latitude = 123.0,
            longitude = 123.0
        ),
        onClickSchool = {  },
    )
}