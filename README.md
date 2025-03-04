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

## Demo

The project includes a `:demo` module that can be run on Android, iOS, and Desktop to test printing.

### Running the Demo

1. **Clone the repository:**
   ```sh
   git clone https://github.com/seu-usuario/multiplatform-print.git
   cd multiplatform-print
   ```

2. **Run on Android:**
wip

3. **Run on iOS:**
wip

4. **Run on Desktop:**
```sh
./gradlew :demo:composeApp:run
```

## ⚠️ Platform Limitations

wip

