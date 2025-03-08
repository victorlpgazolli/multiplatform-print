package dev.victorlpgazolli.printer

import android.content.Context
import android.print.PrintManager
import java.io.File
import java.io.InputStream

class AndroidPrinter(): Printer {
    override fun print(filePath: String) {
        val context = ContextProvider().context ?: return
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = "Printer"
        val fileStream: InputStream = File(filePath).inputStream()
        printManager.print(
            jobName,
            PrinterAdapter(fileStream),
            null
        )
    }
}

actual fun buildPrinter(): Printer = AndroidPrinter()