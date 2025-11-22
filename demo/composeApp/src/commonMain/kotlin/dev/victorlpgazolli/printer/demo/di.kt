package dev.victorlpgazolli.printer.demo

import dev.victorlpgazolli.printer.Printer
import dev.victorlpgazolli.printer.PrinterImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module


internal expect val platformModule: Module

private val commonModule = module {
    single<Printer> {
        PrinterImpl(
            platformPrinter = get(),
            coroutineScope = CoroutineScope(Dispatchers.IO)
        )
    }
}

internal fun printerCommonDiModule() = listOf(
    commonModule,
    platformModule
)