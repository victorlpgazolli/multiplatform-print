package dev.victorlpgazolli.printer

import org.koin.core.module.Module
import org.koin.dsl.module


expect val platformModule: Module

val commonModule = module {
    single<Printer> { PrinterImpl(get()) }
}

fun printerCommonDiModule() = listOf(
    commonModule,
    platformModule
)