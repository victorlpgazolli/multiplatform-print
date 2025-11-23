package dev.victorlpgazolli.printer.demo

actual fun getPlatform(): Platform {
    return object : Platform {
        override val name: String = "WASM JS"
    }
}