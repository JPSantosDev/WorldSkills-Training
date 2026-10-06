package com.example.mod_ctreino.ui.screens

import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mod_ctreino.R
import com.example.mod_ctreino.ui.enums.CurrentScreen
import kotlinx.coroutines.delay
import java.io.File
import kotlin.time.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumoScreen(
    onGravar: () -> Unit,
    onHome: () -> Unit,
    onExplore: () -> Unit,
    onExercises: () -> Unit,
    onArticles: () -> Unit,
    onProfile: () -> Unit
) {

    val context = LocalContext.current
    val dir = remember { File(context.filesDir, "resumos").apply { mkdirs() } }
    val gravacoes = remember {
        mutableStateListOf<File>()
            .apply {
                addAll(dir.listFiles().orEmpty().filter {
                    it.extension == "m4a"
                }.sortedByDescending {
                    it.lastModified()
                }
                )
            }
    }

    var recordingStartedAt by remember { mutableLongStateOf(0L) }
    var isRecording by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }
    var playingPath by remember { mutableStateOf<String?>(null) }
    var recorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var player by remember { mutableStateOf<MediaPlayer?>(null) }
    var currentRecordingFile by remember { mutableStateOf<File?>(null) }

    LaunchedEffect(isRecording) {
        if (isRecording){
            recordingStartedAt = SystemClock.elapsedRealtime()
            while (isRecording){
                val elapsed = SystemClock.elapsedRealtime() - recordingStartedAt
                progress = (elapsed/60_000f).coerceIn(0f,1f)
                if (elapsed >= 60_000) //stoprecording()
                    Toast.makeText(context,"Tempo limite de 60 segundos, gravação salva",Toast.LENGTH_SHORT).show()
                else {
                    delay(100)
                }
            }
        }
    }

    fun stopRecording(discard: Boolean = false){
        val rec = recorder ?: return
        val file = currentRecordingFile

         recorder = null
         currentRecordingFile = null

        val saved = runCatching { rec.stop() }.isSuccess && !discard
        runCatching { rec.release() }
        isRecording = false
        progress = 0f

        if (saved && file != null && file.exists() && file.length() > 0){
            gravacoes.add(0,file)
        } else {
            file?.delete()
            Toast.makeText(context, "Erro ao salvar, tente novamente", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        floatingActionButton = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ){
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .padding(start = 30.dp)
                        .align(Alignment.BottomEnd),
                    progress = {progress}
                )

                FloatingActionButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = {
                        isRecording = !isRecording
                        onGravar()
                    }

                ){
                    Icon(Icons.Default.Mic,contentDescription = null, tint = if (isRecording) Color.Red else Color.Black)
                }
            }

        },
        bottomBar = {
            NavigationBar() {
                NavigationBarItem(
                    selected = true,
                    onClick = onHome,
                    icon = { Icon(Icons.Default.Home,contentDescription = null) },
                    label ={Text("Home")},
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onExplore,
                    icon = { Icon(Icons.Default.Explore,contentDescription = null) },
                    label ={Text("Explore")},
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onExercises,
                    icon = { Icon(Icons.Default.FitnessCenter,contentDescription = null) },
                    label ={Text("Exercícios")},
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onArticles,
                    icon = { Icon(Icons.Default.Note,contentDescription = null) },
                    label ={Text("Artigos")},
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfile,
                    icon = { Icon(Icons.Default.AccountCircle,contentDescription = null) },
                    label ={Text("Perfil")},
                )
            }
        }
    ) {pad->
        Column(Modifier
            .fillMaxSize()
            .padding(pad)
        ) {

        }
    }

}

@Preview
@Composable
fun previewResumO(){
    ResumoScreen(
        onGravar = {  },
        onHome = {  },
        onExplore = {  },
        onExercises = {  },
        onArticles = {  },
        onProfile = {  }



    )
}
