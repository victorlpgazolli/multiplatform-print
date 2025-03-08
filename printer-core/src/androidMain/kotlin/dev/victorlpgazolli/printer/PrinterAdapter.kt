package dev.victorlpgazolli.printer

import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


internal class PrinterAdapter(private val fileInput: InputStream): PrintDocumentAdapter() {
    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        val builder =
            PrintDocumentInfo.Builder("CHANGE ME PLEASE")

        builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
            .build()

        callback!!.onLayoutFinished(
            builder.build(),
            !newAttributes!!.equals(oldAttributes)
        )
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        var input: InputStream? = null
        var output: OutputStream? = null

        try {
            input = fileInput
            output = FileOutputStream(destination!!.fileDescriptor)

            val buf = ByteArray(16384)
            var size: Int

            while ((input.read(buf).also { size = it }) >= 0
                && !cancellationSignal!!.isCanceled
            ) {
                output.write(buf, 0, size)
            }

            if (cancellationSignal!!.isCanceled) {
                callback!!.onWriteCancelled()
            } else {
                callback!!.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            }
        } catch (e: Exception) {
            callback!!.onWriteFailed(e.message)
            println("Exception printing PDF")
        } finally {
            try {
                input!!.close()
                output!!.close()
            } catch (e: IOException) {
                println(
                    "Exception cleaning up from printing PDF"
                )
            }
        }
    }
}