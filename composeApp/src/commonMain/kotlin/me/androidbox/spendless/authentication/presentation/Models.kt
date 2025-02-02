package me.androidbox.spendless.authentication.presentation


enum class KeyButtons(val key: String = "") {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    ZERO("0"),
    DELETE()
}

enum class PinMode(val title: String, val subTitle: String) {
    CREATE("Create PIN", "Use your PIN to login into your account"),
    REPEAT("Repeat your PIN", "Enter your PIN again")
}