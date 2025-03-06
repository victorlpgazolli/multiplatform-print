package dev.victorlpgazolli.printer

import platform.Foundation.NSURL
import platform.UIKit.UIPrintInfo
import platform.UIKit.UIPrintInfoOutputType
import platform.UIKit.UIPrintInteractionController


class IosPrinter(): Printer {
    override fun print(filePath: String) {
        val url = NSURL.fileURLWithPath(filePath)
        val printController = UIPrintInteractionController.sharedPrintController()

        val printInfo = UIPrintInfo.printInfo()
        printInfo.outputType = UIPrintInfoOutputType.UIPrintInfoOutputGeneral
        printInfo.jobName = url.lastPathComponent ?: "Document"

        printController.printInfo = printInfo
        printController.printingItem = url

        printController.presentAnimated(true, completionHandler = null)
    }
}

actual fun buildPrinter(): Printer = IosPrinter()