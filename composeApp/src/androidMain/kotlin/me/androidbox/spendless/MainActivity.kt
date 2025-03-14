package me.androidbox.spendless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.liftric.kvault.KVault
import me.androidbox.spendless.authentication.presentation.CreatePinState
import me.androidbox.spendless.authentication.presentation.components.DeleteKey
import me.androidbox.spendless.authentication.presentation.components.DigitKey
import me.androidbox.spendless.authentication.presentation.components.KeyPad
import me.androidbox.spendless.authentication.presentation.components.PinDots
import me.androidbox.spendless.authentication.presentation.screens.CreatePinScreen
import me.androidbox.spendless.core.presentation.KeyButtons
import me.androidbox.spendless.dashboard.Transaction
import me.androidbox.spendless.onboarding.screens.components.PopularItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            App()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PopularItemPreview() {
    PopularItem(
        popularCategory = Transaction()
    )
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
  /*  TransactionItem(
        transactionModel = TransactionModel("", "", "", "", "",)
    )*/
}

@Preview(showBackground = true)
@Composable
fun CreatePinPreview() {
    CreatePinScreen(
        createPinState = CreatePinState(),
        onAction = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PinDotsPreview() {
    PinDots(
        isFirstDotEnabled = true,
        isSecondDotEnabled = true,
        isThirdDotEnabled = true,
        isFourthDotEnabled = true,
        isFifthDotEnabled = true
    )
}

@Preview(showBackground = true)
@Composable
fun KeyPadPreview() {
    KeyPad(
        onKeyClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun KeyPreview() {
    DigitKey(digit = KeyButtons.SIX, onKeyClicked = {}, enableKeypad = false)
}

@Preview
@Composable
fun BackspaceKeyPreview() {
    DeleteKey {}
}
