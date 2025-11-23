package dev.victorlpgazolli.printer.demo

import dev.victorlpgazolli.printer.PlatformPrinter
import dev.victorlpgazolli.printer.WasmPrinter
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual val platformModule: Module = module {
    single<PlatformPrinter> { WasmPrinter() }
}