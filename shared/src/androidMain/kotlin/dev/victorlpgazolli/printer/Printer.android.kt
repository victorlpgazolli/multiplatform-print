package dev.victorlpgazolli.printer

import android.content.Context
import android.print.PrintManager
import java.io.File
import java.io.InputStream

actual class Printer(
    private val context: Context
) {
    actual fun print(filePath: String) {
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