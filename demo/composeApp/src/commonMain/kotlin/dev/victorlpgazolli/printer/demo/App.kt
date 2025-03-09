package dev.victorlpgazolli.printer.demo

import ScreenshotArea
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import dev.victorlpgazolli.printer.Printer
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
    val screenshotState = rememberScreenshotState(1000)
    val screenshot: State<ImageBitmap?> = screenshotState.startRecording()
        .collectAsState(null)

    val printer = koinInject<Printer>()

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                screenshot.value?.let {
                    printer.print(it)
                }
            },
            modifier = Modifier.padding(20.dp)
        ) {
            Text("Print below content")
        }
        ScreenshotArea(screenshotState) {
            Demo()
        }
    }
}
