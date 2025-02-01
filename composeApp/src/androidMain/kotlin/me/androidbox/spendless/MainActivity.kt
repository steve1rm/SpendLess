package me.androidbox.spendless

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.spendless.authentication.presentation.KeyButtons
import me.androidbox.spendless.authentication.presentation.components.BackspaceKey
import me.androidbox.spendless.authentication.presentation.components.DigitKey
import me.androidbox.spendless.authentication.presentation.components.KeyPad

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
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
    DigitKey(digit = KeyButtons.SIX, onKeyClicked = {})
}

@Preview
@Composable
fun BackspaceKeyPreview() {
    BackspaceKey {}
}
