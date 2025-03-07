package me.androidbox.spendless.authentication.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlinx.coroutines.runBlocking
import me.androidbox.spendless.authentication.domain.imp.generatePinDigest
import kotlin.test.Test


class InsertUserUseCaseImp {
    @Test
    fun testGeneratePinDigest() = runBlocking {
        val userName = "big_spender"
        val pin = "63925"

        assertThat(generatePinDigest(userName, pin).take(30)).isEqualTo("���]+��xk�\b�:���\u00034������(�\\'p�")
    }
}