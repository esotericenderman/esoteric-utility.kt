package foundation.esoteric.utility.byte

import java.security.MessageDigest
import foundation.esoteric.utility.string.sha1
import foundation.esoteric.utility.file.sha1
import java.io.File

/**
 * This method calculates and returns the SHA-1 hash of this `ByteArray`.
 * @return The SHA-1 hash of this `ByteArray`
 * @see String.sha1
 * @see File.sha1
 * @author Esoteric Enderman
 */
fun ByteArray.sha1(): String {
    val bytes = MessageDigest.getInstance("SHA-1").digest(this)
    return bytes.joinToString("") { "%02x".format(it) }
}
