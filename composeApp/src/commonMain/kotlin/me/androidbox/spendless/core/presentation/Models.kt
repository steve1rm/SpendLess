package me.androidbox.spendless.core.presentation

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


enum class Authentication(var title: String, val subTitle: String) {
    AUTHENTICATION_PROMPT("Your Name", subTitle = "Enter your PIN"),
    AUTHENTICATION_FAILED("Too many failed attempts", subTitle = "Try your PIN again in")
}

enum class PinMode(val title: String = "", val subTitle: String = "") {
    CREATE("Create PIN", "Use your PIN to login into your account"),
    REPEAT("Repeat your PIN", "Enter your PIN again"),
    AUTHENTICATION
}

enum class Currency(val symbol: String, val title: String) {
    USD("$", "US Dollar (USD)"),
    EURO("€", "Euro (EUR)"),
    POUND("£", "British Pound Sterling (GBP)"),
    YEN("¥", "Japanese Yen (JPY)"),
 //   SWISS("CHF", "Swiss Franc (CHF)"),
//    CAD("C$", "Canadian Dollar (CAD)"),
 //   AUD("A$", "Australian Dollar (AUD)"),
    CNY("¥", "Chinese Yuan Renminbi (CNY)"),
    INR("₹", "Indian Rupee (INR)"),
    ZAR("R", "South African Rand (ZAR)")
}
