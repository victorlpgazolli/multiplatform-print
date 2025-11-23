package dev.victorlpgazolli.printer

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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

expect val defaultAsyncDispatcher : CoroutineDispatcher

class PrinterImpl(
    private val platformPrinter: PlatformPrinter,
): Printer {

    override suspend fun print(filePath: String) = withContext(defaultAsyncDispatcher) {
        platformPrinter.print(filePath)
    }

    override suspend fun print(imageBitmap: ImageBitmap) {
        val tmpPath =
            "${FileSystem.SYSTEM_TEMPORARY_DIRECTORY}/file.bmp"

        SystemFileSystem.sink(Path(tmpPath)).asOkioSink().buffer()
            .also { imageBitmap.encode(it) }
            .also { it.close() }

        print(tmpPath)

        SystemFileSystem.delete(Path(tmpPath))
    }
}
