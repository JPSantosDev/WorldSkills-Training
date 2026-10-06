package com.example.mod_ctreino.ui.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.mod_ctreino.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtigoScreen(
    onBack: () -> Unit,
    onCompartilhar: () -> Unit,
    onQrCode: () -> Unit,
    onThemeSwitch: () -> Unit,
    onFontChange: () -> Unit,
    onOuvir: () -> Unit
) {
    var isOuvindo by remember { mutableStateOf(false) }
    var lightLevel by remember { mutableFloatStateOf(0f) }
    val sliderState = rememberSliderState(
        value = 0f,
        valueRange = 0f..1f
    )
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack,contentDescription = null)
                    }
                },
                title = {

                },
                actions = {
                    IconButton(onClick = onCompartilhar) {
                        Icon(Icons.Default.Share,contentDescription = null)
                    }
                    IconButton(onClick = onQrCode) {
                        Icon(Icons.Default.QrCode,contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(160.dp),
                actions = {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            IconButton(onClick = onFontChange) {
                                Icon(Icons.Default.TextFields, contentDescription = null)
                            }
                            IconButton(onClick = onThemeSwitch) {
                                Icon(
                                    imageVector = Icons.Default.Settings,contentDescription = null
                                )
                            }
                            Spacer(Modifier.weight(1f))

                            IconButton(
                                modifier = Modifier.width(120.dp),
                                onClick = {
                                    isOuvindo = !isOuvindo
                                    onOuvir()
                                }
                            ) {
                                Row(
                                    modifier = Modifier.width(120.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Headphones,
                                        contentDescription = null
                                    )
                                    Text(if (isOuvindo) "Pausar reprodução" else "Ouvir Artigo")
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.DarkMode,contentDescription = null)

                            Slider(
                                modifier = Modifier.weight(1f),
                                state = sliderState,
                                thumb = {
                                    SliderDefaults.Thumb(
                                        interactionSource = remember {MutableInteractionSource()},
                                        thumbSize = DpSize(16.dp,16.dp),
                                        colors = SliderDefaults.colors(thumbColor = Color.Blue),
                                    )
                                },
                                track = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(8.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color.LightGray)
                                    ){
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(sliderState.value)
                                                .fillMaxHeight()
                                                .background(Color.Blue)
                                        )
                                    }
                                }
                            )

                            Icon(imageVector = Icons.Default.LightMode,contentDescription = null)

                        }
                    }
                }
            )
        }
    ) {
            pad->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(pad),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Física Quântica Explicada", fontWeight = FontWeight.Bold, style = Typography.headlineSmall)
            Text("Por Prof.Roberto Alves ° 22 Fev 2025", fontWeight = FontWeight.Light, style = Typography.bodySmall)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(150.dp)
                    .width(300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.DarkGray)
            ){
                Box(modifier = Modifier
                    .size(48.dp)
                    .background(Color.Gray, CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    Icon(Icons.Default.PlayArrow,contentDescription = null)
                }
            }
            Spacer(Modifier.height(8.dp))

            Text("A física quântica é um ramo da física que " +
                    "surgiu no início do século XX, revolucionando " +
                    "nossa compreensão do universo em escalas " +
                    "muito pequenas. Este artigo explora os " +
                    "conceitos fundamentais da física quântica de " +
                    "forma acessivel.")

            Spacer(Modifier.height(8.dp))
            Text("Princípios Fundamentais", fontWeight = FontWeight.Bold, style = Typography.bodyLarge)
            Spacer(Modifier.height(8.dp))

            Text("O princípio da incerteza de Heisenberg " +
                    "estabelece que não podemos conhecer " +
                    "simultaneamente a posição e o momento de " +
                    "uma particula com precisão absoluta. Quanto " +
                    "mais precisamente conhecemos uma " +
                    "grandeza, menos precisamente podemos")
        }
    }
}


@Preview
@Composable
fun PreviewArtigoScreen() {
    ArtigoScreen(
        onBack = {

        },
        onCompartilhar = {

        },
        onQrCode = {

        },
        onThemeSwitch = {

        },
        onFontChange = {  },
        onOuvir = {  },
    )
}