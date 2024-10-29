package foundation.esoteric.utility.resource

import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ResourceUtilityTest {
    @Test fun resourcesListIsCorrect() {
        val resourcePaths = ResourceUtility.getResourceFilePaths("resource")

        assertEquals(resourcePaths, setOf(Path.of("resource/ResourceUtilityTest/Test Folder/Test File.txt"), Path.of("resource/ResourceUtilityTest/Test File.txt")))
    }

    @Test fun getResourcePathsThrowsCorrectly() {
        var expected: Exception? = null

        try {
            ResourceUtility.getResourceFilePaths("some random path that does not exist")
        } catch (exception: Exception) {
            expected = exception
        }

        assertNotNull(expected)
    }
}
