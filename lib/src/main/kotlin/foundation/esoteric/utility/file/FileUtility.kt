package foundation.esoteric.utility.file

import java.io.File

class FileUtility {
    companion object {
        fun isRecursivelyEmpty(directory: File): Boolean {
            require(directory.isDirectory) { "The specified path is not a directory" }

            val files = directory.listFiles()

            if (files == null || files.size == 0) {
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
    }
}
