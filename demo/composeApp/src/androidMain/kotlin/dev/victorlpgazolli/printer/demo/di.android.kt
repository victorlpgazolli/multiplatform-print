package dev.victorlpgazolli.printer.demo

import dev.victorlpgazolli.printer.AndroidPrinter
import dev.victorlpgazolli.printer.PlatformPrinter
import org.koin.dsl.module

internal actual val platformModule = module {
    single<PlatformPrinter> {
        AndroidPrinter(context = get())
    }
}