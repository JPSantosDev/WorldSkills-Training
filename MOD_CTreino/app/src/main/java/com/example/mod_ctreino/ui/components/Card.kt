package com.example.mod_ctreino.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CardPin(
    modifier: Modifier = Modifier,
    id:Int,
    titulo:String,
    descricao: String,
    data: String

){
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("ID: " + id.toString())
        Text("Título: " + titulo)
        Text("Descrição: "+descricao)
        Text("Data: "+ data)
    }

}

@Preview
@Composable
fun PreviewCard(){
    CardPin(
        id = 1,
        titulo = "Olá",
        descricao = "Sim",
        data = "21/07"
    )
}
