package foundation.esoteric.utility.resource

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.jar.JarFile

/**
 * This method loops through the subfolder of the **resources** folder specified by this `Path` and returns the `Path`s of all files stored in said subfolder.
 * @return A set of all the paths of all files stored in the subfolder specified by this `Path`.
 */
fun Path.getResourceFilePaths(): Set<Path> {
    val filePaths = mutableSetOf<Path>()

    val url = object {}.javaClass.classLoader.getResource(this.toString())?.toURI()

    requireNotNull(url) { "The specified resource URL could not be found." }

    when (url.scheme) {
        "file" -> {
            val folderFile = File(url)
            folderFile.walkTopDown().forEach { file ->
                if (file.isFile) {
                    filePaths.add(Paths.get("$this/${file.relativeTo(folderFile).path}"))
                }
            }
        }
        "jar" -> {
            try {
                val jarFileUrl = url.toURL().openConnection() as java.net.JarURLConnection

                JarFile(jarFileUrl.jarFileURL.path).use { jarFile ->
                    val entries = jarFile.entries()

                    while (entries.hasMoreElements()) {
                        val entry = entries.nextElement()

                        if (entry.name.startsWith(this.toString()) && !entry.isDirectory) {
                            filePaths.add(Paths.get(entry.name))
                        }
                    }
                }
            } catch (exception: Exception) {
                throw IllegalStateException("Failed to access JAR contents.", exception)
            }
        }
    }

    return filePaths
}

/**
 * This method saves the resource in the "resources" folder specified by this `Path` to the file specified as the `outputPath`.
 * @param outputPath The path to the output file.
 */
fun Path.saveResource(outputPath: Path) {
    val resourceStream: InputStream? = object {}.javaClass.classLoader.getResourceAsStream(this.toString())
    requireNotNull(resourceStream) { "Resource '$this' could not be found." }

    Files.createDirectories(outputPath.parent)

    Files.copy(resourceStream, outputPath, StandardCopyOption.REPLACE_EXISTING)
    resourceStream.close()
}
