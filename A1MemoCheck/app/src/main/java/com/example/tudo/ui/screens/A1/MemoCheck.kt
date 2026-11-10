package com.example.tudo.ui.screens.A1

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds




fun fmtTempo(s:Int) = "%02d:%02d".format(s/60,s%60)

class Carta(val par: Int,val img: ImageBitmap){
    var virada by mutableStateOf(true)
}

fun carregarTemas(ctx: Context,pasta: String): List<ImageBitmap> =
    ctx.assets.list(pasta)!!.sorted().map { nome->
        ctx.assets.open("$pasta/$nome").use {
            BitmapFactory.decodeStream(it).asImageBitmap()
        }
    }


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoCheckScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var imgs by remember { mutableStateOf(carregarTemas(context,"memocheck/tema1")) }

    var ocupado by remember { mutableStateOf(false) }

    val galeria = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(4)
    ) { uris ->
        if (uris.size == 4){
            imgs = uris.map { uri->
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver,uri)){ d,_,_ ->
                    d.setTargetSampleSize(4)
                }.asImageBitmap()
            }
        } else if (uris.isNotEmpty()){
            Toast.makeText(context,"Selecione 4 fotos",Toast.LENGTH_SHORT).show()
        }
    }

    var botao by remember { mutableStateOf("START") }
    var segundos by remember { mutableIntStateOf(0) }
    var acertos by remember { mutableIntStateOf(0) }
    var erros by remember { mutableIntStateOf(0) }
    val cartas = remember { mutableStateListOf<Carta>() }

    val jogando = botao == "STOP"

    var primeira by remember { mutableStateOf<Carta?>(null) }
    var resultado by remember { mutableStateOf<String?>(null) }

    var menuTema by remember { mutableStateOf(false) }
    var tema by remember { mutableStateOf("Animais da Amazonia") }


    fun montar(){
        cartas.clear()
        cartas.addAll((imgs+imgs).mapIndexed { index, bitmap ->
            Carta(index%4,bitmap)
        }.shuffled())
    }

    fun encerrar() {
        resultado = "Acertos: $acertos\n Tempo: ${fmtTempo(segundos)}"
        botao = "START"; segundos = 0; acertos = 0; erros = 0
        primeira = null
        ocupado = false
        montar()
    }

    LaunchedEffect(jogando) {
        while (jogando){
            delay(1.seconds)
            segundos++
        }
    }

    LaunchedEffect(imgs) {
        montar()
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("MemoCheck")
                        Spacer(Modifier.weight(1f))
                        Text("Segundos: $segundos ")
                        Text("Erros: $erros ")
                        Text("Acertos: $acertos ")
                    }
                }
            )
        }
    ) { pad->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for(linha in 0 until 2){
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            for (coluna in 0 until 4){
                                val carta = cartas.getOrNull(linha*4+coluna)
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                ){
                                    if (carta != null){
                                        key(carta) {
                                            CardFlip(
                                                carta = carta,
                                                onClick = { carta.virada = !carta.virada }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .width(190.dp)
                        .padding(start = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            if (jogando) encerrar()
                            else scope.launch {
                                for (n in 3 downTo 1) {
                                    botao = "$n"
                                    delay(1.seconds)
                                }

                                cartas.forEach {
                                    it.virada = false
                                }

                                segundos = 0
                                acertos = 0
                                erros = 0
                                botao = "STOP"
                            }
                        },
                        enabled = botao == "START" || jogando,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(botao, fontSize = 16.sp)
                    }

                    Text("Tema", fontWeight = FontWeight.Bold)

                    Box {
                        Row (
                            modifier = Modifier
                                .border(BorderStroke(1.dp,Color.Black))
                                .clickable(enabled = botao == "START"){menuTema = true}
                                .padding(10.dp)
                        ) {
                            Text(tema)
                        }

                        DropdownMenu(
                            expanded = menuTema,
                            onDismissRequest = {menuTema = false}
                        ) {
                            DropdownMenuItem (
                                text = {Text("Animais da Amazonia")},
                                onClick = {
                                    tema = "Animais da Amazonia"
                                    imgs = carregarTemas(context,"memocheck/tema1")
                                    menuTema = false
                                }
                            )

                            DropdownMenuItem (
                                text = {Text("Carros")},
                                onClick = {
                                    tema = "Carros"
                                    imgs = carregarTemas(context,"memocheck/tema2")
                                    menuTema = false
                                }
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            galeria.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        enabled = botao == "START"
                    ) {
                        Icon(Icons.Default.Settings,contentDescription = null)
                    }
                }
            }
        }

        resultado?.let {
            AlertDialog(
                onDismissRequest = {resultado = null},
                confirmButton = { TextButton(onClick = {resultado = null}){Text("OK")} },
                title = {Text("Fim da partida")},
                text = {Text(it)}
            )
        }
    }
}


@Composable
fun CardFlip(
    carta: Carta,
    onClick:() -> Unit
){
    val angulo by animateFloatAsState(
        animationSpec = tween(400),
        targetValue = if (carta.virada) 0f else 180f,
        label = "flip"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { rotationY = angulo; cameraDistance = 12f * density }
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        if(angulo < 90f){
            Image(
                carta.img,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green)
                    .graphicsLayer { rotationY = 180f },
                contentAlignment = Alignment.Center
            ){
                Text("?", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}