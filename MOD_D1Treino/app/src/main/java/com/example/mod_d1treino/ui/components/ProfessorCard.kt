package com.example.mod_d1treino.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mod_d1treino.ui.models.Professor

@Composable
fun ProfessorCard(
    professor: Professor,
    onDelete: () -> Unit
)
{

    var showDelete by remember { mutableStateOf(false) }
    Card(
        shape = CardDefaults.outlinedShape,
        border = BorderStroke(Dp.Hairline, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    showDelete = !showDelete
                }
            ),
        elevation = CardDefaults.cardElevation(
            hoveredElevation = 4.dp,
            pressedElevation = 8.dp,
            defaultElevation = 2.dp
        )
    ) {
        Column(Modifier.fillMaxWidth().padding(8.dp)) {
            Text(professor.nome)
            Text(professor.descricao)
            if (showDelete){
                IconButton(
                    onClick = {
                        onDelete()
                        showDelete = false
                    }
                ) { Icon(Icons.Default.Delete,contentDescription = null) }
            }
            else{
                return@Column
            }
        }
    }

}


@Preview
@Composable
fun ProfessorCardPre(){
    ProfessorCard(
        onDelete = {},
        professor = Professor(
            id = 1,
            nome = "jean",
            email = "açskljdh",
            telefone = "819",
            descricao = "a"
        ),
    )
}