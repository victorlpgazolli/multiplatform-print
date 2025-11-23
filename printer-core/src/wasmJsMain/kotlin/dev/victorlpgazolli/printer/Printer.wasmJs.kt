package dev.victorlpgazolli.printer

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class WasmPrinter(): PlatformPrinter {
    // WIP
    override fun print(filePath: String) {
        val frame = document.createElement(
            "iframe"
        ).apply {
            setAttribute("id", "printingFrame")
            setAttribute("style", "display:none;")
            setAttribute("src", filePath)
            addEventListener("load", {
                window.focus()
            })
            document.body?.appendChild(this)
        }
        val printingFrame = document.getElementById("printingFrame")
        printingFrame?.let {
            val frameWindow = (it as? org.w3c.dom.HTMLIFrameElement)?.contentWindow
            frameWindow?.print()
            document.body?.removeChild(it)
        }
    }
}


actual val defaultAsyncDispatcher: CoroutineDispatcher
    get() = Dispatchers.Default