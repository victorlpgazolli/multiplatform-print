package dev.victorlpgazolli.printer

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.okio.asOkioSink
import okio.FileSystem
import okio.buffer


interface PlatformPrinter {
//  val supportedFileTypes: List<String>
//  fun canPrint(filePath: String): Boolean
//  fun createPDFWrapper(filePath: String): String
    fun print(filePath: String)
}

interface Printer {
    fun print(filePath: String)
    fun print(imageBitmap: ImageBitmap)
}

class PrinterImpl(
    private val platformPrinter: PlatformPrinter
): Printer{
    override fun print(filePath: String) {
        println("printing: $filePath")
        platformPrinter.print(filePath)
    }

    override fun print(imageBitmap: ImageBitmap) {
        val tmpPath = "${FileSystem.SYSTEM_TEMPORARY_DIRECTORY}/file.bmp"

        SystemFileSystem.sink(Path(tmpPath)).asOkioSink().buffer()
            .also { imageBitmap.encode(it) }
            .also { it.close() }

        print(tmpPath)

        SystemFileSystem.delete(Path(tmpPath))

    }
}
