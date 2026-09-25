package com.example.mod_d1treino

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.example.mod_d1treino.ui.screen.DashboardScreen

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import kotlin.jvm.Throws

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
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
    fun scrollUntilVisibleUp(tag: String) {
        repeat(10) {
            if (rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()) {
                return
            }

            rule.onNodeWithTag("mainColumn").performTouchInput {
                swipeUp()
            }
            rule.waitForIdle()
        }
        throw AssertionError("Node with tag not found after scrolling down")
    }

    fun scrollUntilVisibleDown(tag: String){
        repeat(10){
            if (rule.onAllNodesWithTag(tag).fetchSemanticsNodes().isNotEmpty()){
                return
            }
            rule.onNodeWithTag("mainColumn").performTouchInput {
                swipeDown()
            }
            Thread.sleep(300)
        }
        throw AssertionError("Node with tag not found after scrolling down")
    }



    @OptIn(ExperimentalTestApi::class)
    @Test
    fun moduleTest(){

        await()

        rule.onNodeWithTag("btnAdd").performClick()
        await()

        scrollUntilVisibleUp("btnDateInicio")
        rule.onNodeWithTag("btnDateInicio").performClick()
        await()

        rule.onNodeWithContentDescription(
            "Usar o modo de entrada de texto"
        ).performClick()
        await()

        rule.onAllNodes(hasSetTextAction()).fetchSemanticsNodes().forEachIndexed { index, node ->
            Log.i("TEXT_FIELDS", "[$index] = $node")
        }
        try {
            rule.onAllNodes(hasSetTextAction()).onFirst().performTextClearance()
            Thread.sleep(300)
            rule.onAllNodes(hasSetTextAction()).onFirst().performTextInput("25/09/2026")
        } catch (e: Exception) {
            Log.e("ERRO", "Falhou: ${e.message}")
        }

        await()
        rule.onNodeWithText("OK").performClick()
        await()






    }
}