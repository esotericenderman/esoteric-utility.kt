package foundation.esoteric.utility.string

import foundation.esoteric.utility.byte.ByteUtility

class StringUtility {
    companion object {
        /**
         * This method returns the SHA-1 hash of the input string.
         * @param input The string to find the SHA-1 hash of.
         */
        fun getSha1Hash(input: String): String {
            return ByteUtility.getSha1Hash(input.toByteArray())
        }
    }
}
