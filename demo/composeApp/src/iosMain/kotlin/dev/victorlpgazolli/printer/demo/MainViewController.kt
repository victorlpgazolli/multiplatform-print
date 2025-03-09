package dev.victorlpgazolli.printer.demo

import androidx.compose.ui.window.ComposeUIViewController
import dev.victorlpgazolli.printer.printerCommonDiModule
import org.koin.compose.KoinApplication

fun MainViewController() = ComposeUIViewController {
    KoinApplication(
        application = {
            modules(
                printerCommonDiModule()
            )
        }
    ) {
        App()
    }
}