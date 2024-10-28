package foundation.esoteric.utility.resource

import java.io.IOException
import java.util.jar.JarEntry
import java.util.jar.JarFile

/**
 * A collection of static methods that help when working with **resource** files.
 */
class ResourceUtility {
    companion object {
        @Throws(IOException::class)
        private fun getResourceFilePaths(path: String): List<String> {
            val classLoader: ClassLoader = ResourceUtility::class.java.classLoader

            val jarURL = classLoader.getResource(path) ?: return emptyList()

            val jarPath = jarURL.path
            val exclamationMarkIndex = jarPath.indexOf("!")

            val jarPathPrefix = "file:"
            val jarFilePath = jarPath.substring(jarPathPrefix.length, exclamationMarkIndex)

            JarFile(jarFilePath).use { jarFile ->
                val paths = jarFile.stream().map { obj: JarEntry -> obj.name }
                    .filter { name: String -> name.startsWith(path) && name != path }
                    .map { name: String -> name.substring(path.length) }
                    .filter { name: String -> "/" != name }.map { name: String -> path + name }.toList()
                return paths
            }
        }

        /**
         * This method loops through a folder in the **resources** folder and returns the paths of all files stored in said folder.
         * @param path The path to the folder in **resources** to get the file paths of.
         * @return A list of all the paths of all files stored in the folder specified by the path parameter.
         */
        @Throws(IOException::class)
        fun getResourceFilePathsRecursively(path: String): List<String> {
            val paths: MutableList<String> = ArrayList()

            for (resourceFilePath in getResourceFilePaths(path)) {
                val subFiles = getResourceFilePathsRecursively(resourceFilePath)
                if (subFiles.isEmpty()) {
                    paths.add(resourceFilePath)
                } else {
                    paths.addAll(subFiles)
                }
            }

            return paths
        }
    }
}
