---
sidebar_position: 1
sidebar_label: Core printer
---

# Printer Core

`printer-core` provides the main multiplatform printing API.  

## API

### Printer

Main interface used by applications:

```kotlin
interface Printer {
    suspend fun print(filePath: String)
    suspend fun print(imageBitmap: ImageBitmap)
}
```

Usage
1. Print an existing file

```kotlin
printer.print("/path/to/document.pdf") // or any other file type
```

2. Print a `ImageBitmap`

```kotlin
printer.print(imageBitmap)
```

Internally, the image is encoded into a temporary BMP file, printed via the platform, and then removed.

### Creating a Printer instance

Use the `PrinterImpl` class to create a multiplatform printer instance, passing the platform-specific `PlatformPrinter` implementation as a parameter.
In the following example, we use [Koin](https://insert-koin.io/) for dependency injection:

```kotlin
// commonMain
single<Printer> {
    PrinterImpl(
        platformPrinter = instance(),
    )
}

// androidMain
single<PlatformPrinter> {
    AndroidPrinter(context = androidContext())
}

// iosMain
single<PlatformPrinter> { IosPrinter() }

// desktopMain
single<PlatformPrinter> { DesktopPrinter() }
```