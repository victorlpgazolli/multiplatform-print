# <a href='https://multiplatform-print.victorlpgazolli.dev'><img src='docs/static/img/logo.png' height='60' alt='Multiplatform print logo' aria-label='title' style="display: flex;align-items: center;"/>Kotlin Multiplatform Print</a>

[![Docs](https://github.com/victorlpgazolli/multiplatform-print/actions/workflows/docs-deploy.yml/badge.svg)](https://github.com/victorlpgazolli/multiplatform-print/actions/workflows/docs-deploy.yml)
[![Publish](https://github.com/victorlpgazolli/multiplatform-print/actions/workflows/publish.yml/badge.svg)](https://github.com/victorlpgazolli/multiplatform-print/actions/workflows/publish.yml)
![badge](https://img.shields.io/badge/platform-android-blue)
![badge](https://img.shields.io/badge/platform-ios-blue)
![badge](https://img.shields.io/badge/platform-desktop-blue)

**Multiplatform Print** is a Kotlin Multiplatform library for printing files on Android, iOS, and Desktop.

It provides two packages to handle printing:
- `printer-core`
- `printer-compose`


You can see the library in action by running the `:demo` module, but ill provide a video of all platforms running demo module, and a quick overview of the library usage below.


## Demo:

![demo](https://github.com/victorlpgazolli/multiplatform-print/blob/master/docs/static/video/demo.mp4)


## `printer-core`

Main package to print stuff, the library exposes a common API:
```kotlin
interface Printer {
//  send to printer any file, regardless of the extension. 
//  no error handling, ill just trust you in this one
    fun print(filePath: String)
    
//  send to printer an ImageBitmap, in case you want to print a custom image
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
            // print any file extension
            printer.print(filePath)
            
            val imageBitmap = ImageBitmap(100, 100)
            // or just print an imageBitmap
            printer.print(imageBitmap)
        }
    ) {
        Text("Send to printer!!!")
    }
}

```
## `printer-compose`

Library used to take screenshots of `@Composables` or just record them in a set time interval.

```kotlin

interface ScreenshotState {
//  consider the following param as "frame rate", default 20ms 
    val refreshScreenshotTimeout: Long
    
//  graphicsLayer used to draw the content of the composable to a ImageBitmap
    val graphicsLayer: GraphicsLayer

//  flow that emits the ImageBitmap of the current composable
    fun startRecording(): Flow<ImageBitmap>
    
//  one-shot function that takes a screenshot of the current composable
    suspend fun takeScreenshot(): ImageBitmap
}

// Composable that wraps the content to be recorded/taken a screenshot
fun ScreenshotArea(
    screenshotState: ScreenshotState,
    content: @Composable () -> Unit,
): Unit {}


```


### `printer-compose` Usage

```kotlin
// screenshot usage:

@Composable
fun App() {
    val screenshotState = rememberScreenshotState()


    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                coroutineScope.launch {
                    // take a screenshot of the current value:
                    val screenshot: ImageBitmap = screenshotState.takeScreenshot()
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
    // refreshScreenshotTimeout param is optional, if not provided the default value is 20ms
    val screenshotState by rememberScreenshotState(1000)
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
            // when the count changes the UI, screenshotState will collect the new value
            Text("Hello World. Count: $count") 
        }
    }
   
}

```


## Project Roadmap

- [x] basic implementation
- [x] CI/CD setup
- [x] add support for more file types (using file convertion)
- [ ] add support for wasmJs target
- [x] add support for printing `@Composables`


