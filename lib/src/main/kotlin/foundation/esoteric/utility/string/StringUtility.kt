package foundation.esoteric.utility.string

import java.security.MessageDigest

class StringUtility {
    companion object {
        fun getSha1Hash(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}
