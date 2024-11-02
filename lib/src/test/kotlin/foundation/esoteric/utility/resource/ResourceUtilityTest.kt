package foundation.esoteric.utility.resource

import org.junit.jupiter.api.assertThrows
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourceUtilityTest {
    @Test fun resourcesListIsCorrect() {
        val resourcePaths = Path("resource").getResourceFilePaths()

        assertEquals(resourcePaths, setOf(Path.of("resource/ResourceUtilityTest/Test Folder/Test File.txt"), Path.of("resource/ResourceUtilityTest/Test File.txt")))
    }

    @Test fun getResourcePathsThrowsCorrectly() {
        assertThrows<IllegalArgumentException> {
            Path("some random path that does not exist").getResourceFilePaths()
        }
    }
}
