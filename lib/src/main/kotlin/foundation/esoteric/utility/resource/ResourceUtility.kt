package foundation.esoteric.utility.resource

import java.io.File
import java.util.jar.JarFile

/**
 * A collection of static methods that help when working with **resource** files.
 */
class ResourceUtility {
    companion object {
        fun getResourceFilePaths(folderPath: String): List<String> {
            val filePaths = mutableListOf<String>()

            val url = object {}.javaClass.classLoader.getResource(folderPath)?.toURI() ?: return emptyList()

            when {
                url.scheme == "file" -> {
                    val folderFile = File(url)
                    folderFile.walkTopDown().forEach { file ->
                        if (file.isFile) {
                            filePaths.add("$folderPath/${file.relativeTo(folderFile).path}")
                        }
                    }
                }
                url.scheme == "jar" -> {
                    val jarPath = url.path.substringBefore("!").removePrefix("file:")
                    JarFile(jarPath).use { jarFile ->
                        val entries = jarFile.entries()
                        while (entries.hasMoreElements()) {
                            val entry = entries.nextElement()
                            if (entry.name.startsWith(folderPath) && !entry.isDirectory) {
                                filePaths.add(entry.name)
                            }
                        }
                    }
                }
            }

            return filePaths
        }
    }
}
