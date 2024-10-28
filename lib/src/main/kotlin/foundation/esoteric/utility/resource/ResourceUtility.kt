package foundation.esoteric.utility.resource

import java.io.IOException
import java.util.jar.JarEntry
import java.util.jar.JarFile

class ResourceUtility {
    companion object {
        @Throws(IOException::class)
        fun getResourceFilePaths(path: String): List<String> {
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
