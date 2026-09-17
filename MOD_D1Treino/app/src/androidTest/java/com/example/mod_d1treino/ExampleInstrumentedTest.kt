package com.example.mod_d1treino

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mod_d1treino.ui.screen.DashboardScreen

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    var text by mutableStateOf("")
    val wait = 3000

    @get:Rule
    val rule = createComposeRule()

    fun await() = rule.run {
        waitForIdle()
        Thread.sleep(wait.toLong())
    }

    @Before
    fun Main() {
        rule.setContent {
            AppRoot()
        }
    }

    @Test
    fun moduleTest(){
        rule.onNodeWithTag("buscaDash").performTextInput("@#$")
        await()
        rule.onNodeWithTag("buscaDash").performTextReplacement("Matemática-Avançada")
        await()
        rule.onNodeWithTag("buscaDash").performTextReplacement("Mate")
        await()
        rule.onNodeWithTag("buscaDash").performTextReplacement("Física Quântica")
        await()
        rule.onNodeWithTag("buscaDash").performTextClearance()
        await()
        rule.onNodeWithTag("gridTrue").performClick()
        await()
        rule.onNodeWithTag("curso_5").performTouchInput { longClick() }
        await()
        rule.onNodeWithTag("gridFalse").performClick()
        await()
        rule.onNodeWithTag("curso_5").performTouchInput { click() }

    }
}