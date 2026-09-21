package com.example.mod_a1treino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mod_a1treino.ui.screens.QuizMyBrainScreen
import com.example.mod_a1treino.ui.theme.MOD_A1TreinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MOD_A1TreinoTheme {
                QuizMyBrainScreen(
                    onHome = {}
                )
            }
        }
    }
}



@Composable
fun AppRoot(){

}
