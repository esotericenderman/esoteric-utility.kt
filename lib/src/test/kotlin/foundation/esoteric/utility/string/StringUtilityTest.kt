package foundation.esoteric.utility.string

import kotlin.test.Test
import kotlin.test.assertEquals

class StringUtilityTest {
    @Test fun sha1Works() {
        val string = "A string with a few characters in it."

        assertEquals("0e7f418083ad73f5006b5ffa1e7ddce617936a64", string.sha1())
    }
}
