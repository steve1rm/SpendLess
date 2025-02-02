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
import me.androidbox.spendless.authentication.presentation.CreatePinActions
import me.androidbox.spendless.authentication.presentation.CreatePinState
import me.androidbox.spendless.authentication.presentation.KeyButtons
import me.androidbox.spendless.authentication.presentation.components.KeyPad
import me.androidbox.spendless.authentication.presentation.components.PinDots

@Composable
fun CreatePinScreen(
    modifier: Modifier = Modifier,
    createPinState: CreatePinState,
    onAction: (action: CreatePinActions) -> Unit,
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
                    isFirstDotEnabled = createPinState.createPinList.count() < 1,
                    isSecondDotEnabled = createPinState.createPinList.count() < 2,
                    isThirdDotEnabled = createPinState.createPinList.count() < 3,
                    isFourthDotEnabled = createPinState.createPinList.count() < 4,
                    isFifthDotEnabled = createPinState.createPinList.count() < 5
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                KeyPad(
                    onKeyClicked = { keyButton ->
                        if(keyButton == KeyButtons.DELETE) {
                            onAction(CreatePinActions.OnDeletePressed)
                        }
                        else {
                            onAction(CreatePinActions.OnPinNumberEntered(keyButton))
                        }
                    }
                )
            }
        }
    )
}
