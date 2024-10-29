package foundation.esoteric.utility.file

import org.apache.commons.io.FileUtils
import java.io.File
import kotlin.math.exp
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

        var expected: Exception? = null

        try {
            FileUtility.isRecursivelyEmpty(file)
        } catch (exception: Exception) {
            expected = exception
        }

        assertNotNull(expected)
    }

    @AfterTest fun deleteRunDirectory() {
        FileUtils.deleteDirectory(run)
    }
}
