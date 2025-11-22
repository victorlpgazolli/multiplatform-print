package dev.victorlpgazolli.printer

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.CoroutineScope


import kotlinx.coroutines.launch
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.okio.asOkioSink
import okio.FileSystem
import okio.buffer


interface PlatformPrinter {
    fun print(filePath: String)
}

interface Printer {
    suspend fun print(filePath: String)
    suspend fun print(imageBitmap: ImageBitmap)
}

class PrinterImpl(
    private val platformPrinter: PlatformPrinter,
    private val coroutineScope: CoroutineScope
): Printer {

    override suspend fun print(filePath: String) {
        coroutineScope.launch {
            platformPrinter.print(filePath)
        }
    }

    override suspend fun print(imageBitmap: ImageBitmap) {
        coroutineScope.launch {
            val tmpPath =
                "${FileSystem.SYSTEM_TEMPORARY_DIRECTORY}/file-${imageBitmap.hashCode()}.bmp"

            SystemFileSystem.sink(Path(tmpPath)).asOkioSink().buffer()
                .also { imageBitmap.encode(it) }
                .also { it.close() }

            print(tmpPath)
        }
    }
}
