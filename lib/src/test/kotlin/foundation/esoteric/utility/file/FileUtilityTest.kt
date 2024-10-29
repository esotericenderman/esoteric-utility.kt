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

    @AfterTest fun deleteRunDirectory() {
        FileUtils.deleteDirectory(run)
    }
}
