package foundation.esoteric.utility.string

import foundation.esoteric.utility.byte.getSha1Hash

/**
 * This method returns the SHA-1 hash of this `String`.
 */
fun String.getSha1Hash(): String {
    return this.toByteArray().getSha1Hash()
}
