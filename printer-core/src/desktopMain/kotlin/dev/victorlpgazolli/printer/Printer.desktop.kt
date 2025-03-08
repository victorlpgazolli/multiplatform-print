package dev.victorlpgazolli.printer

import java.io.FileInputStream
import javax.print.DocFlavor
import javax.print.PrintServiceLookup
import javax.print.SimpleDoc

class DesktopPrinter(): Printer {
    override fun print(filePath: String) {
        val document = SimpleDoc(
            FileInputStream(filePath),
            DocFlavor.INPUT_STREAM.AUTOSENSE,
            null
        )
        val printerService = PrintServiceLookup.lookupDefaultPrintService()
        printerService?.let { // in case you dont have a printer :)
            it.createPrintJob().print(document, null)
        }
    }
}

actual fun buildPrinter(): Printer = DesktopPrinter()