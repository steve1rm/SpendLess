package me.androidbox.spendless.core.domain

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import dev.whyoleg.cryptography.algorithms.SHA512
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.bytestring.encodeToByteString
import me.androidbox.spendless.transactions.data.TransactionTable

suspend fun generatePinDigest(username: String, pin: String): String {
    val hash: ByteString = CryptographyProvider.Default
        .get(SHA512)
        .hasher()
        .hash((username+pin).encodeToByteString())

    return hash.decodeToString()
}

suspend fun encryptTransaction(transaction: TransactionTable): String {
// using example here: https://whyoleg.github.io/cryptography-kotlin/examples/#aes-gcm
    val provider = CryptographyProvider.Default
    val aesGcm = provider.get(AES.GCM)
    val keyGenerator = aesGcm.keyGenerator(AES.Key.Size.B256)
    val key: AES.GCM.Key = keyGenerator.generateKey()
    val cipher = key.cipher()

    val transactionToEncode = transaction.toString()
    println("txn to encode: $transactionToEncode")

    return cipher.encrypt(plaintext = transactionToEncode.encodeToByteArray()).decodeToString()
}