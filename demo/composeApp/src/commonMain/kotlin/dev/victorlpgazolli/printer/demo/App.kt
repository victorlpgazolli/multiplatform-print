package dev.victorlpgazolli.printer.demo

import ScreenshotArea
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import dev.victorlpgazolli.printer.Printer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import rememberScreenshotState
import kotlin.time.Duration.Companion.milliseconds

import kotlin.time.Duration.Companion.seconds


@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            Content()
        }
    }
}

@Composable
fun Content() {
    val coroutineScope = rememberCoroutineScope()

    val refreshRate = remember { 10.milliseconds }

    val screenshotState = rememberScreenshotState(refreshRate)

    var count by remember { mutableStateOf(0) }
    var filePath by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            count++
        }
    }

    val screen by screenshotState.startRecording().collectAsState(null)

    val printer = koinInject<Printer>()

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = filePath ?: "",
            onValueChange = {
                filePath = it
            }
        )
        Button(
            enabled = filePath != null,
            onClick = {
                coroutineScope.launch {
                    screenshotState
                        .takeScreenshot()
                        .also {
                            filePath?.let {
                                printer.print(it)
                            }
                        }
                }
            },
            modifier = Modifier.padding(20.dp)
        ) {
            Text("Print selected path")
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    screenshotState
                        .takeScreenshot()
                        .also { printer.print(it) }
                }
            },
            modifier = Modifier.padding(20.dp)
        ) {
            Text("Print content")
        }

        ScreenshotArea(screenshotState) {
            Demo(count)
        }

        Spacer(Modifier.padding(top = 20.dp).background(Color.LightGray).fillMaxWidth())
        Text("Recording of the content above using $refreshRate refresh rate.")

        screen?.let {
            Image(
                bitmap = it,
                contentDescription = "recording"
            )
        }
    }
}
