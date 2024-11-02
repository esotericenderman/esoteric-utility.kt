package foundation.esoteric.utility.string

import kotlin.test.Test
import kotlin.test.assertEquals

class StringUtilityTest {
    @Test fun sha1HashWorks() {
        val string = "A string with a few characters in it."

        assertEquals(string.getSha1Hash(), "0e7f418083ad73f5006b5ffa1e7ddce617936a64")
    }
}
