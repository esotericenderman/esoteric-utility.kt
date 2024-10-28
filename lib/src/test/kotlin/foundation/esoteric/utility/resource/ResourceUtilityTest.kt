package foundation.esoteric.utility.resource

import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourceUtilityTest {
    @Test fun resourcesListIsCorrect() {
        val resourcePaths = ResourceUtility.getResourceFilePaths("resource")

        assertEquals(resourcePaths, setOf(Path.of("resource/ResourceUtilityTest/Test Folder/Test File.txt"), Path.of("resource/ResourceUtilityTest/Test File.txt")))
    }
}
