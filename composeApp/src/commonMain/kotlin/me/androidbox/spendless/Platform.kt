package me.androidbox.spendless

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform