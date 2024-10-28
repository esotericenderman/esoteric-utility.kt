package foundation.esoteric.utility.resource

import java.io.File
import java.util.jar.JarFile

/**
 * A collection of static methods that help when working with **resource** files.
 */
class ResourceUtility {
    companion object {
        /**
         * This method loops through a folder in the **resources** folder and returns the paths of all files stored in said folder.
         * @param path The path to the folder in **resources** to get the file paths of.
         * @return A list of all the paths of all files stored in the folder specified by the path parameter.
         */
        fun getResourceFilePaths(path: String): List<String> {
            val filePaths = mutableListOf<String>()

            val url = object {}.javaClass.classLoader.getResource(path)?.toURI() ?: return emptyList()

            when {
                url.scheme == "file" -> {
                    val folderFile = File(url)
                    folderFile.walkTopDown().forEach { file ->
                        if (file.isFile) {
                            filePaths.add("$path/${file.relativeTo(folderFile).path}")
                        }
                    }
                }
                url.scheme == "jar" -> {
                    val jarPath = url.path.substringBefore("!").removePrefix("file:")
                    JarFile(jarPath).use { jarFile ->
                        val entries = jarFile.entries()
                        while (entries.hasMoreElements()) {
                            val entry = entries.nextElement()
                            if (entry.name.startsWith(path) && !entry.isDirectory) {
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
