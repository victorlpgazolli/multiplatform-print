---
sidebar_position: 2
sidebar_label: Recording a Composable
---

# Recording a Composable

`printer-compose` allows you to **record a composable in real time**, generating
a continuous flow of `ImageBitmap` frames.

This is useful for:

- Dynamic UI previews  
- Time-based visual exports
- Debug purposes
- ??

---

## Basic Usage

```kotlin
@Composable
fun App() {
    var count by remember { mutableStateOf(0) }

    // increment counter every second
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            count++
        }
    }

    // record at 1 frame per second
    val screenshotState = rememberScreenshotState(refreshRate = 1.seconds)

    // collect the emitted frames
    val frame by screenshotState.startRecording().collectAsState(null)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        frame?.let {
            Image(bitmap = it, contentDescription = "Recorded frame")
        }

        ScreenshotArea(screenshotState) {
            Text("Count: $count")
        }
    }
}
```

## How It Works

`startRecording()` internally:

1. Creates a loop based on refreshRate and returns a `Flow<ImageBitmap>`
2. Renders your composable using the state’s GraphicsLayer
3. For every iteration it converts the graphics layer from your composable to a `ImageBitmap`
4. Emit the latest `ImageBitmap`

