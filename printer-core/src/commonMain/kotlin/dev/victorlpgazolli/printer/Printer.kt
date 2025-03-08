package dev.victorlpgazolli.printer

import androidx.compose.ui.graphics.ImageBitmap


interface Printer {
//  val supportedFileTypes: List<String>
//  fun canPrint(filePath: String): Boolean
//  fun createPDFWrapper(filePath: String): String
    fun print(filePath: String)
}
expect fun buildPrinter(): Printer

class PrinterImpl(
    private val printer: Printer = buildPrinter()
){

    fun print(filePath: String) {
        // todo add logs
        printer.print(filePath)
    }

    fun print(imageBitmap: ImageBitmap) {
//        val file = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//            "screenshot-${System.currentTimeMillis()}.png"
//        )
    }
}