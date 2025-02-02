package me.androidbox.spendless.authentication.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.androidbox.spendless.authentication.presentation.KeyButtons
import me.androidbox.spendless.authentication.presentation.components.KeyPad
import me.androidbox.spendless.authentication.presentation.components.PinDots

@Composable
fun CreatePinScreen(
    modifier: Modifier = Modifier,
    listOfPinNumbers: List<Int>,
    onKeyEntered: (keyButton: KeyButtons) -> Unit
) {
    Scaffold(
        topBar = {

        },
        content = {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PinDots(
                    isFirstDotEnabled = listOfPinNumbers.count() < 1,
                    isSecondDotEnabled = listOfPinNumbers.count() < 2,
                    isThirdDotEnabled = listOfPinNumbers.count() < 3,
                    isFourthDotEnabled = listOfPinNumbers.count() < 4,
                    isFifthDotEnabled = listOfPinNumbers.count() < 5
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                KeyPad(
                    onKeyClicked = { keyButtons ->
                       onKeyEntered(keyButtons)
                    }
                )
            }
        }
    )
}
