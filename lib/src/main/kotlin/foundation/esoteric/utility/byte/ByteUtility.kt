package foundation.esoteric.utility.byte

import java.security.MessageDigest

/**
 * This method returns the SHA-1 hash of this `ByteArray`.
 */
fun ByteArray.getSha1Hash(): String {
    val bytes = MessageDigest.getInstance("SHA-1").digest(this)
    return bytes.joinToString("") { "%02x".format(it) }
}
