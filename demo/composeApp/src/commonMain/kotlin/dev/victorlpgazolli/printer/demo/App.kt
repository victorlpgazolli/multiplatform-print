package dev.victorlpgazolli.printer.demo

import ScreenshotArea
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.victorlpgazolli.printer.Printer
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import rememberScreenshotState


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

    val screenshotState = rememberScreenshotState(1000)

    val printer = koinInject<Printer>()

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
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
            Demo()
        }
    }
}
