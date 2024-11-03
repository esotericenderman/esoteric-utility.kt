package foundation.esoteric.utility.file

import foundation.esoteric.utility.byte.sha1
import foundation.esoteric.utility.string.sha1
import net.lingala.zip4j.ZipFile
import java.io.File
import java.io.IOException
import java.nio.file.Path

/**
 * This method checks if this directory is **recursively empty**, which means either:
 * - The directory is empty.
 * - The directory contains only **recursively empty** directories.
 *
 * Note that this `File` **must** be a directory to use this method.
 * @return Whether this directory is **recursively empty**.
 * @throws IllegalArgumentException If this directory does not exist.
 * @throws IllegalArgumentException If this `File` is not a directory.
 * @see Path.isRecursivelyEmpty
 * @author Esoteric Enderman
 */
fun File.isRecursivelyEmpty(): Boolean {
    require(this.exists()) { "The specified directory does not exist." }
    require(this.isDirectory) { "The specified path is not a directory." }
    require(!this.isFile) { "The specified path is a file" }

    val files = this.listFiles()

    if (files == null || files.isEmpty()) {
        return true
    }

    for (file in files) {
        if (file.isFile) {
            return false
        } else if (file.isDirectory) {
            if (!file.isRecursivelyEmpty()) {
                return false
            }
        }
    }

    return true
}

/**
 * This method checks if the directory with this `Path` is **recursively empty**, which means either:
 * - The directory is empty.
 * - The directory contains only **recursively empty** directories.
 *
 * Note that this `Path` **must** lead to a directory to use this method.
 * @return Whether the directory that this path leads to is **recursively empty**.
 * @throws IllegalArgumentException If the file that this path leads to does not exist.
 * @throws IllegalArgumentException If this `Path` does not lead to a directory.
 * @see File.isRecursivelyEmpty
 * @author Esoteric Enderman
 */
fun Path.isRecursivelyEmpty(): Boolean {
    return this.toFile().isRecursivelyEmpty()
}

/**
 * This method computes and returns the SHA-1 hash of this `File`.
 *
 * Note that this `File` must not be a directory for this method to work.
 * @return The SHA-1 hash of this `File`.
 * @throws IllegalArgumentException If this `File` does not exist.
 * @throws IllegalArgumentException If this `File` is a directory.
 * @see Path.sha1
 * @see String.sha1
 * @see ByteArray.sha1
 * @author Esoteric Enderman
 */
fun File.sha1(): String {
    require(this.exists()) { "File does not exist." }
    require(this.isFile) { "File is not a file." }
    require(!this.isDirectory) { "File is a directory." }

    val bytes = this.readBytes()
    return bytes.sha1()
}

/**
 * This method returns the SHA-1 hash of the file that this `Path` leads to.
 *
 * Note that this `Path` must not lead to a directory for this method to work.
 * @return The SHA-1 hash of the file that this `Path` leads to.
 * @throws IllegalArgumentException If the file does not exist.
 * @throws IllegalArgumentException If the file is a directory.
 * @see File.sha1
 * @see String.sha1
 * @see ByteArray.sha1
 * @author Esoteric Enderman
 */
fun Path.sha1(): String {
    return this.toFile().sha1()
}

/**
 * This method creates a zip archive of the directory that this `File` represents and outputs it at the specified location parameter `zipFile`.
 * @param zipFile The `File` location of where to output the zip archive.
 * @return The newly created zip `File`.
 * @throws IllegalArgumentException If this directory does not exist.
 * @throws IllegalArgumentException If this file is not a directory.
 * @see Path.zip
 * @author Esoteric Enderman
 */
@Throws(IOException::class)
fun File.zip(zipFile: File): File {
    require(this.isDirectory)
    require(!this.isFile)
    requireNotNull(this.listFiles()) { "Cannot list files of the source folder." }
    require(this.exists()) { "Source folder does not exist." }

    ZipFile(zipFile).use { zipFileInstance ->
        for (file in this.listFiles()!!) {
            if (file.isDirectory) {
                zipFileInstance.addFolder(file)
            } else {
                zipFileInstance.addFile(file)
            }
        }
    }

    return zipFile
}

/**
 * This method creates a zip archive of the directory that this `Path` leads to and outputs it at the specified location parameter `zipFilePath`.
 * @param zipFilePath The `Path` that specifies the output of the zip archive.
 * @return The newly created zip `File`.
 * @throws IllegalArgumentException If the directory that this `Path` leads to does not exist.
 * @throws IllegalArgumentException If the file that this `Path` leads to is not a directory.
 * @see File.zip
 * @author Esoteric Enderman
 */
@Throws(IOException::class)
fun Path.zip(zipFilePath: Path): File {
    return this.toFile().zip(zipFilePath.toFile())
}
