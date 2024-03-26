package i.ll.algo.go

import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import java.io.File
import java.io.FileNotFoundException

class FileManager : KoinComponent {

    private val log = KotlinLogging.logger {}

    fun <T> getData(filename: String, creator: (String) -> T): List<T> {
        try {
            File("${System.getProperty("INPUT_DIR")}$filename")
            return File(filename).readLines().map { creator(it) }
        } catch (e: FileNotFoundException) {
            log.error { "Unable to read file: $filename" }
            throw e
        }
    }
}
