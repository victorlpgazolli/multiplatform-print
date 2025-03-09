package dev.victorlpgazolli.printer

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<PlatformPrinter> { DesktopPrinter() }
}