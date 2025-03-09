package dev.victorlpgazolli.printer

import android.content.Context
import android.print.PrintManager
import java.io.File
import java.io.InputStream

class AndroidPrinter(
    private val context: Context
): PlatformPrinter {

    override fun print(filePath: String) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = "Printer"
        val fileStream: InputStream = File(filePath).inputStream()
        println(fileStream)
        printManager.print(
            jobName,
            PrinterAdapter(fileStream),
            null
        )
    }
}
