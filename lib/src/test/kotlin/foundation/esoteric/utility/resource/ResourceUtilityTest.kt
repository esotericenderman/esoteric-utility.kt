package foundation.esoteric.utility.resource

import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.test.*

class ResourceUtilityTest {

    private var run = File("run")

    @BeforeTest fun createRunDirectory() {
        run.mkdir()
    }

    @Test fun resourcesListIsCorrect() {
        val resourcePaths = Path("resource").resourceFilePaths()

        assertEquals(setOf(Path.of("resource/ResourceUtilityTest/Test Folder/Test File.txt"), Path.of("resource/ResourceUtilityTest/Test File.txt")), resourcePaths)
    }

    @Test fun getResourcePathsThrowsCorrectly() {
        assertThrows<IllegalArgumentException> {
            Path("some random path that does not exist").resourceFilePaths()
        }
    }

    @Test fun savingResourceWorks() {
        val saveLocation = File(run, "Test File.txt")
        val resourcePath = Path("resource/ResourceUtilityTest/Test File.txt")
        resourcePath.saveResource(saveLocation.toPath())

        assertTrue(saveLocation.exists())
        assertTrue(saveLocation.isFile)
        assertFalse(saveLocation.isDirectory)
        assertEquals("This file is used to test the resource utility.", saveLocation.readText().trimEnd('\n', '\r'))
    }

    @Test fun savingResourcesWorks() {
        val saveFolder = File(run, "Save Folder")
        val resourcePath = Path.of("resource")
        resourcePath.saveResources(saveFolder.toPath())

        assertTrue(saveFolder.exists())
        assertTrue(saveFolder.isDirectory)
        assertFalse(saveFolder.isFile)

        val resourceUtilityTest = File(saveFolder, "ResourceUtilityTest")
        assertTrue(resourceUtilityTest.exists())
        assertTrue(resourceUtilityTest.isDirectory)
        assertFalse(resourceUtilityTest.isFile)

        val deepFile = File(run, "Save Folder/ResourceUtilityTest/Test Folder/Test File.txt")
        assertTrue(deepFile.exists())
        assertTrue(deepFile.isFile)
        assertFalse(deepFile.isDirectory)
        assertEquals("This file is used to test the resource utility.", deepFile.readText().trimEnd('\n', '\r'))
    }

    @AfterTest fun deleteRunDirectory() {
        FileUtils.deleteDirectory(run)
    }
}
