package foundation.esoteric.utility.file

import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.assertThrows
import java.io.File
import kotlin.test.*

class FileUtilityTest {

    private val run = File("run")

    @BeforeTest fun createRunDirectory() {
        run.mkdir()
    }

    @Test fun emptyDirectoryIsRecursivelyEmpty() {
        assertTrue(FileUtility.isRecursivelyEmpty(run))

        FileUtils.deleteDirectory(run)
    }

    @Test fun recursivelyEmptyCheckWorks() {
        File("run/directory-one").mkdir()
        File("run/directory-two").mkdir()

        File("run/directory-two/directory-three").mkdir()
        File("run/directory-two/directory-four").mkdir()

        assertTrue(FileUtility.isRecursivelyEmpty(run))
    }

    @Test fun filledDirectoryIsntRecursivelyEmpty() {
        File("run/file.txt").createNewFile()

        assertFalse(FileUtility.isRecursivelyEmpty(run))
    }

    @Test fun deeplyFilledDirectoryIsntRecursivelyEmpty() {
        File("run/directory").mkdir()
        File("run/directory/file.txt").createNewFile()

        assertFalse(FileUtility.isRecursivelyEmpty(run))
    }

    @Test fun recursiveEmptinessThrowsWhenNotDirectory() {
        val file = File("run/file.txt")
        file.createNewFile()

        assertThrows<IllegalArgumentException> {
            FileUtility.isRecursivelyEmpty(file)
        }
    }

    @Test fun recursiveEmptinessThrowsWhenNonExistent() {
        val file = File("run/file.txt")

        assertThrows<IllegalArgumentException> {
            FileUtility.isRecursivelyEmpty(file)
        }
    }

    @Test fun sha1FileHashWorks() {
        val file = File(run, "file.txt")

        file.createNewFile()
        file.writeText("Some sample text to test the SHA-1 file hash function.")

        assertEquals(FileUtility.getSha1Hash(file), "d954f0153df726daae33c93f6928fadbfb15fa92")
    }

    @Test fun sha1FileThrowsWhenFileNonExistent() {
        val file = File(run, "file.txt")

        assertThrows<IllegalArgumentException> {
            FileUtility.getSha1Hash(file)
        }
    }

    @Test fun sha1FileThrowsWhenFileIsDirectory() {
        assertThrows<IllegalArgumentException> {
            FileUtility.getSha1Hash(run)
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
        FileUtility.zipFolder(folderToZip, resultFile)

        assertTrue(resultFile.exists())
    }

    @AfterTest fun deleteRunDirectory() {
        FileUtils.deleteDirectory(run)
    }
}
