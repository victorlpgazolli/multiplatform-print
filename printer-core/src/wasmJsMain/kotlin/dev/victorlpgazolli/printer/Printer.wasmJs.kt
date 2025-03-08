package dev.victorlpgazolli.printer

class WasmPrinter(): Printer {
    override fun print(filePath: String) {
    }
}

actual fun buildPrinter(): Printer = WasmPrinter()