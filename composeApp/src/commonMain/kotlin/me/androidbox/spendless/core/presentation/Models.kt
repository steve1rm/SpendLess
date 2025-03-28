package me.androidbox.spendless.core.presentation

import org.jetbrains.compose.resources.DrawableResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.clothing
import spendless.composeapp.generated.resources.education
import spendless.composeapp.generated.resources.entertainment
import spendless.composeapp.generated.resources.food
import spendless.composeapp.generated.resources.health
import kotlin.reflect.typeOf

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
    SWISS("CHF", "Swiss Franc (CHF)"),
    CAD("C$", "Canadian Dollar (CAD)"),
    AUD("A$", "Australian Dollar (AUD)"),
    CNY("¥", "Chinese Yuan Renminbi (CNY)"),
    INR("₹", "Indian Rupee (INR)"),
    ZAR("R", "South African Rand (ZAR)"),
    THB("฿", "Thailand Baht (THB)")
}

enum class TransactionItems(val title: String, val iconRes: DrawableResource) {
    ENTERTAINMENT("Entertainment", Res.drawable.entertainment),
    CLOTHING("Clothing & Accessories", Res.drawable.clothing),
    EDUCATION("Education", Res.drawable.education),
    FOOD("Food & Groceries", Res.drawable.food),
    HEALTH("Health & Wellness", Res.drawable.health),
}

sealed interface PreferenceType {
    val type: String
        get() = ""
    val recipient: String
        get() = ""
    val typeName: String
        get() = ""
}

enum class TransactionType(override val type: String, override val typeName: String) : PreferenceType {
    RECEIVER(type = "Expense", typeName = "Receiver"),
    SENDER(type = "Income", typeName = "Sender")
}

enum class ExpensesFormat(override val type: String) : PreferenceType {
    NEGATIVE("-$10"),
    BRACKET("($10)")
}

enum class DecimalSeparator(override val type: String, val symbol: Char) : PreferenceType {
    DOT("1.00", '.'),
    COMMA("1,00", ',')
}

enum class ExpiryDuration(override val type: String) : PreferenceType {
    FIVE_MINS("5 mins"),
    FIFTEEN_MINUS("15 mins"),
    THIRTY_MINUS("30 mins"),
    ONE_HOUR("1 hour")
}

enum class LockedDuration(override val type: String) : PreferenceType {
    FIFTEEN_SECS("15s"),
    THIRTY_SECS("30s"),
    ONE_MIN("1 min"),
    FIVE_MINS("5 min")
}

enum class ThousandsSeparator(override val type: String, val symbol: Char) : PreferenceType {
    DOT("1.000", '.'),
    COMMA("1,000", ','),
    SPACE("1 000", ' ')
}