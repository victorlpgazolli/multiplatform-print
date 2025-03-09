package dev.victorlpgazolli.printer.demo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.victorlpgazolli.printer.printerCommonDiModule
import org.koin.compose.KoinApplication


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Printer",
    ) {
        KoinApplication (
            application = {
                modules(
                    printerCommonDiModule()
                )
            }
        ) {
            App()
        }
    }
}