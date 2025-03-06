# 🚧 WIP: Multiplatform Print

**Multiplatform Print** is a Kotlin Multiplatform library for printing files on Android, iOS, and Desktop.

## Usage

The library exposes a common API:

```kotlin
expect class Printer() {
    fun print(filePath: String)
}
```

Each platform provides its own implementation.

## Project Roadmap

- [x] basic implementation
- [] CI/CD setup
- [] add support for more file types (using file convertion)
- [] add support for wasmJs target
- [] add support for printing `@Composables`

## Demo

The project includes a `:demo` module that can be run on Android, iOS, and Desktop.

## ⚠️ Platform Limitations


| Platform | Current file extension support |
|----------|--------------------------|
| Android  | pdf |
| iOS      | pdf |
| Desktop  | pdf |


