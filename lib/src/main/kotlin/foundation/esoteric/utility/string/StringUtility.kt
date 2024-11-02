package foundation.esoteric.utility.string

import foundation.esoteric.utility.byte.getSha1Hash
import foundation.esoteric.utility.file.getSha1Hash
import java.io.File

/**
 * This method returns the SHA-1 hash of this `String`.
 * @return The SHA-1 hash of this `String`
 * @see ByteArray.getSha1Hash
 * @see File.getSha1Hash
 * @author Esoteric Enderman
 */
fun String.getSha1Hash(): String {
    return this.toByteArray().getSha1Hash()
}
