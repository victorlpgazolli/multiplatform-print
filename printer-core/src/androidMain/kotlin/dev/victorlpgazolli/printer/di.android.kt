package dev.victorlpgazolli.printer

import org.koin.dsl.module

actual val platformModule = module {
    single<PlatformPrinter> {
        AndroidPrinter(context = get())
    }
}