package foundation.esoteric.utility.file

import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FileUtilityTest {
    @Test fun recursivelyEmptyCheckWorks() {
        File("run").mkdir()

        File("run/empty-directory").mkdir()
        assertTrue(FileUtility.isRecursivelyEmpty(File("run/empty-directory")))

        File("run/non-empty-directory").mkdir()
        File("run/non-empty-directory/file.txt").createNewFile()
        assertFalse(FileUtility.isRecursivelyEmpty(File("run/non-empty-directory")))
    }
}
