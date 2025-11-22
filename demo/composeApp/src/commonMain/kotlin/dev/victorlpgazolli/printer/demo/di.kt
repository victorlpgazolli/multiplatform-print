package dev.victorlpgazolli.printer.demo

import dev.victorlpgazolli.printer.Printer
import dev.victorlpgazolli.printer.PrinterImpl
import org.koin.core.module.Module
import org.koin.dsl.module


internal expect val platformModule: Module

private val commonModule = module {
    single<Printer> {
        PrinterImpl(
            platformPrinter = get(),
        )
    }
}

internal fun printerCommonDiModule() = listOf(
    commonModule,
    platformModule
)