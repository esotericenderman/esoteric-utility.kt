package foundation.esoteric.utility.file

import org.apache.commons.io.FileUtils
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

    @Test fun filledDirectoryIsNotRecursivelyEmpty() {
        File("run/file.txt").createNewFile()

        assertFalse(FileUtility.isRecursivelyEmpty(run))
    }

    @Test fun deeplyFilledDirectoryIsNotRecursivelyEmpty() {
        File("run/directory").mkdir()
        File("run/directory/file.txt").createNewFile()

        assertFalse(FileUtility.isRecursivelyEmpty(run))
    }

    @AfterTest fun deleteRunDirectory() {
        FileUtils.deleteDirectory(run)
    }
}
