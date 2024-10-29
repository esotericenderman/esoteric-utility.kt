package foundation.esoteric.utility.string

import java.security.MessageDigest

class StringUtility {
    companion object {
        /**
         * This method returns the SHA-1 hash of the input string.
         * @param input The string to find the SHA-1 hash of.
         */
        fun getSha1Hash(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-1").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}
