package com.example.mod_ctreino.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.io.File
import java.util.Collections.addAll


@Composable
fun ResumoScreen(
    onHome: () -> Unit,
    onExplore: () -> Unit,
    onExercises: () -> Unit,
    onArticles: () -> Unit,
    onPerfil: () -> Unit
) {


    var file by remember { mutableStateOf<File?>(null) }
    var recorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var recording by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dir = remember { File(context.filesDir,"resumos").apply { mkdirs() } }
    var progress by remember { mutableStateOf(0f) }

    val gravacoes = remember {
        mutableStateListOf<File>().apply {
            addAll( dir.listFiles().orEmpty().filter { it.extension == "m4a" && it.length() > 0 }
                .sortedByDescending { it.lastModified() }
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.S)
    fun startRecording(){
        if (recording) return

        file = File(dir,"resumo_${System.currentTimeMillis()}.m4a")

        try {
            val novoRecorder = MediaRecorder(context).apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(file?.absoluteFile)
                setMaxDuration(60000)
                prepare()
                start()
            }
            recorder = novoRecorder
            recording = true

        } catch (e: Exception){



            recorder?.release()
            recorder = null
            recording = false
            file?.delete()
            file = null

        }
    }

    fun stopRecording(discard: Boolean = false){
        val _recorder = recorder ?: return
        val _file = file

        recorder = null
        file = null
        val saved = runCatching { _recorder.stop() }.isSuccess && !discard
        runCatching { _recorder.release() }
        recording = false
        progress = 0f

        if (saved && _file != null && _file.exists() && _file.length() > 0){
            gravacoes.add(0,_file)
        }
        else{
            _file?.delete()
        }
    }

    val persmissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){
            granted ->
        if (granted) startRecording()
        else{
            Toast.makeText(context,"Permissão negada",Toast.LENGTH_SHORT).show()
        }

    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = onHome, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Home,contentDescription = null) }
                    IconButton(onClick = onExplore, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Explore,contentDescription = null) }
                    IconButton(onClick = onExercises, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Book,contentDescription = null) }
                    IconButton(onClick = onArticles, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Article,contentDescription = null) }
                    IconButton(onClick = onPerfil, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.AccountBox,contentDescription = null) }
                }
            )
        },
        floatingActionButton = {
            IconButton(
                onClick = {
                    if (recording){
                        stopRecording()
                    }
                    else if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
                        startRecording()
                    }
                    else{
                        persmissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    }

                }
            ) {
                Icon(Icons.Default.Mic,contentDescription = null)
            }
        }
    ) {pad->
        Column(modifier = Modifier
            .padding(pad)
        ) {



        }
    }
}