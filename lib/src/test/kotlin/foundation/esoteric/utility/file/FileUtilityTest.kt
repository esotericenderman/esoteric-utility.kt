package foundation.esoteric.utility.file

import foundation.esoteric.utility.resource.saveResource
import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.io.path.Path
import kotlin.test.*

class FileUtilityTest {

    private val run = File("run")

    @BeforeTest fun createRunDirectory() {
        run.mkdir()
    }

    @Test fun emptyDirectoryIsRecursivelyEmpty() {
        assertTrue(run.isRecursivelyEmpty())

        FileUtils.deleteDirectory(run)
    }

    @Test fun recursivelyEmptyCheckWorks() {
        File("run/directory-one").mkdir()
        File("run/directory-two").mkdir()

        File("run/directory-two/directory-three").mkdir()
        File("run/directory-two/directory-four").mkdir()

        assertTrue(run.isRecursivelyEmpty())
    }

    @Test fun filledDirectoryIsntRecursivelyEmpty() {
        File("run/file.txt").createNewFile()

        assertFalse(run.isRecursivelyEmpty())
    }

    @Test fun deeplyFilledDirectoryIsntRecursivelyEmpty() {
        File("run/directory").mkdir()
        File("run/directory/file.txt").createNewFile()

        assertFalse(run.isRecursivelyEmpty())
    }

    @Test fun recursiveEmptinessThrowsWhenNotDirectory() {
        val file = File("run/file.txt")
        file.createNewFile()

        assertThrows<IllegalArgumentException> {
            file.isRecursivelyEmpty()
        }
    }

    @Test fun recursiveEmptinessThrowsWhenNonExistent() {
        val file = File("run/file.txt")

        assertThrows<IllegalArgumentException> {
            file.isRecursivelyEmpty()
        }
    }

    @Test fun sha1FileHashWorks() {
        val file = File(run, "file.txt")

        file.createNewFile()
        file.writeText("Some sample text to test the SHA-1 file hash function.")

        assertEquals("d954f0153df726daae33c93f6928fadbfb15fa92", file.sha1())
    }

    @Test fun sha1ZipFileHashWorks() {
        val resourcePack = File(run.path, "TestPluginResourcePack.zip")

        Path("file/FileUtilityTest/TestPluginResourcePack.zip").saveResource(resourcePack.toPath())

        assertTrue(resourcePack.exists())
        assertTrue(resourcePack.isFile)
        assertFalse(resourcePack.isDirectory)

        assertEquals("c276751b2c56bc44bce393fb3356c0bd9f3a91b4", resourcePack.sha1())
    }

    @Test fun sha1FileThrowsWhenFileNonExistent() {
        val file = File(run, "file.txt")

        assertThrows<IllegalArgumentException> {
            file.sha1()
        }
    }

    @Test fun sha1FileThrowsWhenFileIsDirectory() {
        assertThrows<IllegalArgumentException> {
            run.sha1()
        }
    }

    @Test fun zippingFolderWorks() {
        val folderToZip = File(run, "folder-to-zip")
        folderToZip.mkdir()

        val fileOne = File(folderToZip, "file-1.txt")
        val fileTwo = File(folderToZip, "file-2.txt")

        fileOne.createNewFile()
        fileTwo.createNewFile()

        fileOne.writeText("Some sample text.")
        fileTwo.writeText("Some other sample text.")

        val subFolder = File(folderToZip, "subfolder")
        subFolder.mkdir()

        val fileThree = File(subFolder, "file-3.txt")
        fileThree.createNewFile()

        fileThree.writeText("Third sample text.")

        val extraFolder = File(run, "result-folder")
        extraFolder.mkdir()

        val resultFile = File(extraFolder, "result.zip")
        folderToZip.zip(resultFile)

        assertTrue(resultFile.exists())
    }

    @Test fun zipFolderThrowsWhenFolderNonExistent() {
        assertThrows<IllegalArgumentException> {
            File(run, "folder-that-does-not-exist").zip(File(run, "result.zip"))
        }
    }

    @AfterTest fun deleteRunDirectory() {
        FileUtils.deleteDirectory(run)
    }
}
