package foundation.esoteric.utility.byte

import java.security.MessageDigest
import foundation.esoteric.utility.string.getSha1Hash
import foundation.esoteric.utility.file.getSha1Hash
import java.io.File

/**
 * This method calculates and returns the SHA-1 hash of this `ByteArray`.
 * @return The SHA-1 hash of this `ByteArray`
 * @see String.getSha1Hash
 * @see File.getSha1Hash
 * @author Esoteric Enderman
 */
fun ByteArray.getSha1Hash(): String {
    val bytes = MessageDigest.getInstance("SHA-1").digest(this)
    return bytes.joinToString("") { "%02x".format(it) }
}
