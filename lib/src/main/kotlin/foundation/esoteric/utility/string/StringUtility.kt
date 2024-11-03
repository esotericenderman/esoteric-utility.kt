package foundation.esoteric.utility.string

import foundation.esoteric.utility.byte.sha1
import foundation.esoteric.utility.file.sha1
import java.io.File

/**
 * This method returns the SHA-1 hash of this `String`.
 * @return The SHA-1 hash of this `String`
 * @see ByteArray.sha1
 * @see File.sha1
 * @author Esoteric Enderman
 */
fun String.sha1(): String {
    return this.toByteArray().sha1()
}
