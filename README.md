# 🚧 WIP: Multiplatform Print

**Multiplatform Print** is a Kotlin Multiplatform library for printing files on Android, iOS, and Desktop.

It provides two packages to handle printing:
- `printer-core`
- `printer-compose`


You can see the library in action by running the `:demo` module, but ill provide a video of all platforms running demo module, and a quick overview of the library usage below.


## Demo:



## `printer-core`

Main package to print stuff, the library exposes a common API:
```kotlin
interface Printer {
    fun print(filePath: String)
    fun print(imageBitmap: ImageBitmap)
}
```

### `printer-core` Usage 

```kotlin

@Composable
fun App() {
    val printer = koinInject<Printer>()

    Button(
        onClick = {
            val filePath = "/path/to/random/file"
            printer.print(filePath) // print any file extension
            
            val imageBitmap = ImageBitmap(100, 100)
            printer.print(imageBitmap) // or just print an imageBitmap
        }
    ) {
        Text("Send to printer!!!")
    }
}

```
## `printer-compose`

Library used to take screenshots of `@Composables` or just record them in a set time interval.

```kotlin
// screenshot usage:

@Composable
fun App() {
    val screenshotState = rememberScreenshotState()


    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                coroutineScope.launch {
                    val screenshot: ImageBitmap = screenshotState.takeScreenshot() // take a screenshot of the current value
                }
            }
        ) { Text("Print below content") }
        ScreenshotArea(screenshotState) {
            Text("Hello World!")
        }
    }
   
}


```

```kotlin
// recording screen usage:

@Composable
fun App() {
    var count by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            count++
        }
    }
    
    val screenshotState by rememberScreenshotState(1000) // refreshScreenshotTimeout is optional, if not provided the default value is 20ms
        .startRecording()
        .collectAsState(null)

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        screenshotState?.let {
            Image(
                bitmap = it,
                contentDescription = "recording"
            )
        }
        ScreenshotArea(screenshotState) {
            Text("Hello World. Count: $count") // when the count changes the UI, screenshotState will collect the new value
        }
    }
   
}

```


## Project Roadmap

- [x] basic implementation
- [ ] CI/CD setup
- [x] add support for more file types (using file convertion)
- [ ] add support for wasmJs target
- [x] add support for printing `@Composables`


