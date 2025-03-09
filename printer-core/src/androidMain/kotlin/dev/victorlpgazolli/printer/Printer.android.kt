package dev.victorlpgazolli.printer

import android.content.Context
import android.graphics.BitmapFactory
import androidx.print.PrintHelper
import java.io.File
import java.io.InputStream

class AndroidPrinter(
    private val context: Context
): PlatformPrinter {
    private val jobName = "Printer"

    override fun print(filePath: String) {
        val fileStream: InputStream = File(filePath).inputStream()

        PrintHelper(context)
            .apply { scaleMode = PrintHelper.SCALE_MODE_FIT }
            .also { printHelper ->
                val bitmap = BitmapFactory.decodeStream(fileStream)
                printHelper.printBitmap(jobName, bitmap)
            }
    }
}
