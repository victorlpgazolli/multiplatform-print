package dev.victorlpgazolli.printer

import androidx.compose.ui.window.ComposeUIViewController
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSUserDomainMask
import platform.Foundation.writeToFile

fun createFile(fileName: String, content: String): String {
    val fileManager = NSFileManager.defaultManager
    val paths = NSSearchPathForDirectoriesInDomains(
        directory = NSDocumentDirectory,
        domainMask = NSUserDomainMask,
        expandTilde = true
    )

    // Obtém o diretório de documentos do usuário
    val documentsDirectory = paths.firstOrNull() as? String
        ?: return "Error: Unable to get documents directory"

    // Caminho completo do arquivo
    val filePath = "$documentsDirectory/$fileName"

    // Escreve o conteúdo no arquivo
    @OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
    val success = (content as NSString).writeToFile(
        filePath,
        atomically = true,
        encoding = NSUTF8StringEncoding,
        error = null
    )

    return if (success) filePath else "Error: Unable to write file"
}
fun MainViewController() = ComposeUIViewController {
    createFile("file.txt", "teste")
    val paths = NSSearchPathForDirectoriesInDomains(
        directory = NSDocumentDirectory,
        domainMask = NSUserDomainMask,
        expandTilde = true
    )
    App(){
        Printer().print("${paths.firstOrNull()}/file.txt")
    }
}