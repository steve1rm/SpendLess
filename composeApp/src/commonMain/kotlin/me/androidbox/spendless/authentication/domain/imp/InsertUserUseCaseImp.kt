package me.androidbox.spendless.authentication.domain.imp

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA512
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.bytestring.encodeToByteString
import me.androidbox.spendless.data.SpendLessDataSource
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.domain.InsertUserUseCase

class InsertUserUseCaseImp(
    private val spendLessDataSource: SpendLessDataSource
) : InsertUserUseCase {
    override suspend fun execute(user: User){
        val pinHash: String = generatePinDigest(user.username, user.pin)
        println("PIN: ${user.pin}")
        println("PINHASH: ${pinHash}")

        val securedUser = User(
            username = user.username,
            pin = pinHash
        )

        spendLessDataSource.insertUser(securedUser)
    }

    private suspend fun generatePinDigest (username: String, pin: String): String {
        println("generatePinDigest: ${username+pin}")
        val hash: ByteString = CryptographyProvider.Default
            .get(SHA512)
            .hasher()
            .hash((username+pin).encodeToByteString())
        return hash.decodeToString()
    }
}