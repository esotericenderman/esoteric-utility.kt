package foundation.esoteric.utility.resource

import kotlin.test.Test

class ResourceUtilityTest {
    @Test fun resourcesListIsCorrect() {
        val resourcePaths = ResourceUtility.getResourceFilePaths("resource/Test Folder")

        println(resourcePaths.toString())
    }
}
