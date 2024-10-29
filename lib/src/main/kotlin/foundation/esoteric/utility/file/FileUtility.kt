package foundation.esoteric.utility.file

import foundation.esoteric.utility.string.StringUtility
import java.io.File
import java.nio.file.Path

/**
 * This class provides various static utility methods for working with files.
 */
class FileUtility {
    companion object {
        /**
         * This method checks if a directory is **recursively empty**, which means either:
         * - The directory is empty.
         * - The directory contains only **recursively empty** directories.
         * @param directory The directory to check for recursive emptiness.
         */
        fun isRecursivelyEmpty(directory: File): Boolean {
            require(directory.exists()) { "The specified directory does not exist." }
            require(directory.isDirectory) { "The specified path is not a directory." }

            val files = directory.listFiles()

            if (files == null || files.isEmpty()) {
                return true
            }

            for (file in files) {
                if (file.isFile) {
                    return false
                } else if (file.isDirectory) {
                    if (!isRecursivelyEmpty(file)) {
                        return false
                    }
                }
            }

            return true
        }

        /**
         * This method checks if a directory is **recursively empty**, which means either:
         * - The directory is empty.
         * - The directory contains only **recursively empty** directories.
         * @param directoryPath The path of the directory to check for recursive emptiness.
         */
        fun isRecursivelyEmpty(directoryPath: Path): Boolean {
            return isRecursivelyEmpty(directoryPath.toFile())
        }

        /**
         * This method checks if a directory is **recursively empty**, which means either:
         * - The directory is empty.
         * - The directory contains only **recursively empty** directories.
         * @param directoryPath The path of the directory to check for recursive emptiness.
         */
        fun isRecursivelyEmpty(directoryPath: String): Boolean {
            return isRecursivelyEmpty(File(directoryPath))
        }

        fun getSha1Hash(filePath: String): String {
            val file = File(filePath)

            require(file.exists()) { "File does not exist." }
            require(file.isFile) { "File is not a file." }

            val content = file.readText()
            return StringUtility.getSha1Hash(content)
        }
    }
}
