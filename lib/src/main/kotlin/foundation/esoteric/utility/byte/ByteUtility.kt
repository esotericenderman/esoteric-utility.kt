package foundation.esoteric.utility.byte

import java.security.MessageDigest

class ByteUtility {
    companion object {
        /**
         * This method returns the SHA-1 hash of the input bytes.
         * @param input The byte array to find the SHA-1 hash of.
         */
        fun getSha1Hash(input: ByteArray): String {
            val bytes = MessageDigest.getInstance("SHA-1").digest(input)
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}
