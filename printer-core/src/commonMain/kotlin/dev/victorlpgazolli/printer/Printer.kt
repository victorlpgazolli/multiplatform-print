package dev.victorlpgazolli.printer

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.toPixelMap
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.okio.asOkioSink
import okio.BufferedSink
import okio.FileSystem
import okio.buffer


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
        val finalPath = "${FileSystem.SYSTEM_TEMPORARY_DIRECTORY}/file.bmp"
        println("finalPath: $finalPath")
        val bufferedSink = SystemFileSystem.sink(Path(finalPath)).asOkioSink().buffer()

        encode(
            BitmapWrapper(imageBitmap.toPixelMap()),
            bufferedSink
        )
        bufferedSink.close()

    }
}

class BitmapWrapper(
    private val pixels: PixelMap,
) {
    val width: Int = pixels.width
    val height: Int = pixels.height

    fun get(x: Int, y: Int): Color = pixels[x, y]

    fun red(x: Int, y: Int): Int = (pixels[x, y].red * 255).toInt()

    fun green(x: Int, y: Int): Int = (pixels[x, y].green * 255).toInt()

    fun blue(x: Int, y: Int): Int = (pixels[x, y].blue * 255).toInt()
}

fun encode(bitmap: BitmapWrapper, sink: BufferedSink) {
    val height = bitmap.height
    val width = bitmap.width
    val bytesPerPixel = 3
    val rowByteCountWithoutPadding = bytesPerPixel * width
    val rowByteCount = (rowByteCountWithoutPadding + 3) / 4 * 4
    val pixelDataSize = rowByteCount * height
    val bmpHeaderSize = 14
    val dibHeaderSize = 40

    // BMP Header
    sink.writeUtf8("BM") // ID.
    sink.writeIntLe(bmpHeaderSize + dibHeaderSize + pixelDataSize) // File size.
    sink.writeShortLe(0) // Unused.
    sink.writeShortLe(0) // Unused.
    sink.writeIntLe(bmpHeaderSize + dibHeaderSize) // Offset of pixel data.

    // DIB Header
    sink.writeIntLe(dibHeaderSize)
    sink.writeIntLe(width)
    sink.writeIntLe(height)
    sink.writeShortLe(1) // Color plane count.
    sink.writeShortLe(bytesPerPixel * Byte.SIZE_BITS)
    sink.writeIntLe(0) // No compression.
    sink.writeIntLe(16) // Size of bitmap data including padding.
    sink.writeIntLe(2835) // Horizontal print resolution in pixels/meter. (72 dpi).
    sink.writeIntLe(2835) // Vertical print resolution in pixels/meter. (72 dpi).
    sink.writeIntLe(0) // Palette color count.
    sink.writeIntLe(0) // 0 important colors.

    // Pixel data.
    for (y in height - 1 downTo 0) {
        for (x in 0 until width) {
            sink.writeByte(bitmap.blue(x, y))
            sink.writeByte(bitmap.green(x, y))
            sink.writeByte(bitmap.red(x, y))
        }

        // Padding for 4-byte alignment.
        for (p in rowByteCountWithoutPadding until rowByteCount) {
            sink.writeByte(0)
        }
    }
}