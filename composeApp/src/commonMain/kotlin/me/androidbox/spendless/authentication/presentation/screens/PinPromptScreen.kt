package me.androidbox.spendless.authentication.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.authentication.presentation.CreatePinActions
import me.androidbox.spendless.authentication.presentation.CreatePinState
import me.androidbox.spendless.authentication.presentation.KeyButtons
import me.androidbox.spendless.authentication.presentation.components.KeyPad
import me.androidbox.spendless.authentication.presentation.components.PinDots
import me.androidbox.spendless.core.presentation.Error
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.OnSurfaceVariant
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.createpin
import spendless.composeapp.generated.resources.logout

@Composable
fun PinPromptScreen(
    modifier: Modifier = Modifier,
    createPinState: CreatePinState,
    onAction: (action: CreatePinActions) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        modifier = Modifier.size(44.dp).background(color = Color.LightGray.copy(alpha = 0.5f),
                            RoundedCornerShape(16.dp)
                        ),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = vectorResource(resource = Res.drawable.logout),
                            contentDescription = "Go back",
                            tint = Error
                        )

                    }
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {

                Column(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = vectorResource(resource = Res.drawable.createpin),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = createPinState.countdownTime.toString(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W600,
                        color = OnSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = createPinState.pinMode.subTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = OnSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(32.dp))

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
                            if (keyButton == KeyButtons.DELETE) {
                                onAction(CreatePinActions.OnDeletePressed)
                            } else {
                                onAction(CreatePinActions.OnPinNumberEntered(keyButton))
                            }
                        }
                    )
                }

                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    visible = createPinState.shouldShowRedBanner,
                    content = {
                        Box(
                            modifier = Modifier.fillMaxWidth().height(height = 72.dp)
                                .background(color = Color.Red),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Wrong Pin",
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                )
            }
        }
    )
}