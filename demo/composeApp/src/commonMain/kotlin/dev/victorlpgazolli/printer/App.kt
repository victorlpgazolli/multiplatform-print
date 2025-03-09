package dev.victorlpgazolli.printer

import ScreenshotArea
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.delay
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

    var count by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            count++
        }
    }


    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                screenshot.value?.let {
                    printer.print(it)
                }
            }
        ) {
            Text("print")
        }
        ScreenshotArea(screenshotState) {
            Text(
                text = "Texto exemplo, contagem atual: $count",
            )
        }
        screenshot.value?.let {
            Image(it, contentDescription = "screenshot")
        }


    }
}
