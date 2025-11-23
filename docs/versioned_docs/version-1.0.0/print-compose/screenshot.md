---
sidebar_label: Screenshot a composable
sidebar_position: 3
---

# Screenshot a composable

The `print-compose` module allows you to capture a Composable as an
image (`ImageBitmap`)


## 1. Creating the state

``` kotlin
val screenshotState = rememberScreenshotState()
```

## 2. Registering the Composable to be captured

Wrap the desired content inside `ScreenshotArea`:

``` kotlin
ScreenshotArea(
    screenshotState = screenshotState
) {
    Box(
        Modifier
            .size(200.dp)
            .background(Color.Red)
    )
}
```

The content inside `ScreenshotArea` is what will be converted into a [`androidx.compose.ui.graphics.ImageBitmap`](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/ImageBitmap)

## 3. Capturing the image

Use:

``` kotlin
val bitmap = screenshotState.takeScreenshot()
```

`bitmap` will be an instance of `ImageBitmap` representing the content when `screenshotState.takeScreenshot()` was called.