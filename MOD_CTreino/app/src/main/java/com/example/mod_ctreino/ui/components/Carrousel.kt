package com.example.mod_ctreino.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mod_ctreino.ui.model.DadosDto
import com.example.mod_ctreino.ui.repository.DataRepository

@Composable
fun Carrousel(
    artigo: List<DadosDto>
) {
    val paginas = artigo.take(3)

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { paginas.size }
    )

    Column(
        modifier = Modifier
            .width(300.dp)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .width(300.dp)
                .height(140.dp)
        ) { page ->

            val pagina = paginas[page]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(
                    text = pagina.titulo,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Data: ${pagina.data}"
                )

                Text(
                    text = "Descrição: ${pagina.descricao}"
                )

                Text(
                    text = "ID: ${pagina.id}"
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            paginas.indices.forEach { index ->

                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) {
                                Color.Gray
                            } else {
                                Color.Black
                            }
                        )
                )
            }
        }
    }
}
