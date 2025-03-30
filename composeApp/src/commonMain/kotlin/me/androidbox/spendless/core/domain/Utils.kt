package me.androidbox.spendless.core.domain

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA512
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.bytestring.encodeToByteString

suspend fun generatePinDigest(pin: String): String {
    val hash: ByteString = CryptographyProvider.Default
        .get(SHA512)
        .hasher()
        .hash((pin).encodeToByteString())

    return hash.decodeToString()
}