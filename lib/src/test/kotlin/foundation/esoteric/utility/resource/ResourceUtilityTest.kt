package foundation.esoteric.utility.resource

import org.junit.jupiter.api.assertThrows
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourceUtilityTest {
    @Test fun resourcesListIsCorrect() {
        val resourcePaths = ResourceUtility.getResourceFilePaths("resource")

        assertEquals(resourcePaths, setOf(Path.of("resource/ResourceUtilityTest/Test Folder/Test File.txt"), Path.of("resource/ResourceUtilityTest/Test File.txt")))
    }

    @Test fun getResourcePathsThrowsCorrectly() {
        assertThrows<IllegalArgumentException> {
            ResourceUtility.getResourceFilePaths("some random path that does not exist")
        }
    }
}
