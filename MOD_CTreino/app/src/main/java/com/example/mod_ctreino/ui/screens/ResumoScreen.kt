package com.example.mod_ctreino.ui.screens

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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mod_ctreino.ui.enums.CurrentScreen

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
    var sliderState = rememberSliderState(
        value = 0f,
        valueRange = 0f..60f
    )
    var isRecording by remember { mutableStateOf(false) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = onHome,
                    icon = {Icon(Icons.Default.Home,contentDescription = null)},
                    label = {Text("Home")}
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onExplore,
                    icon = {Icon(Icons.Default.Explore,contentDescription = null)},
                    label = {Text("Explore")}
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onExercises,
                    icon = {Icon(Icons.Default.FitnessCenter,contentDescription = null)},
                    label = {Text("Exercícios")}
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onArticles,
                    icon = {Icon(Icons.Default.Note,contentDescription = null)},
                    label = {Text("Artigos")}
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onProfile,
                    icon = {Icon(Icons.Default.AccountCircle,contentDescription = null)},
                    label = {Text("Perfil")}
                )
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)

            ) {
                Slider(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start =40.dp)
                            .align(Alignment.BottomCenter),
                    state = sliderState
                )
                FloatingActionButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    containerColor = Color.White,
                    onClick = {
                        isRecording = !isRecording
                        onGravar()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Mic,
                        contentDescription = null,
                        tint = if (isRecording) Color.Red else Color.Black
                    )
                }
            }
        }
    )
    { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxWidth()
        ) {


        }
    }
}


@Preview
@Composable
fun ResumoScreenPreview(){
    ResumoScreen(
        onGravar = {  },
        onHome = {  },
        onExplore = {  },
        onExercises = {  },
        onArticles = {  },
        onProfile = {  }
    )
}