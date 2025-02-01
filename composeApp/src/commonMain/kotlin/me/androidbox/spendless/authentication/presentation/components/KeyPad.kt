package me.androidbox.spendless.authentication.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.authentication.presentation.KeyButtons
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.PrimaryFixed
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.backspace

@Composable
fun KeyPad(
    modifier: Modifier = Modifier,
    onKeyClicked: (keyButton: KeyButtons) -> Unit
) {
    Column(
        modifier = modifier
            .width(intrinsicSize = IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DigitKey(
                digit = KeyButtons.ONE,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
            DigitKey(digit = KeyButtons.TWO,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
            DigitKey(digit = KeyButtons.THREE,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
        }

        Row(
            modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DigitKey(digit = KeyButtons.FOUR,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
            DigitKey(digit = KeyButtons.FIVE,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
            DigitKey(digit = KeyButtons.SIX,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
        }

        Row(
            modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DigitKey(digit = KeyButtons.SEVEN,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
            DigitKey(digit = KeyButtons.EIGHT,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
            DigitKey(digit = KeyButtons.NINE,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
        }

        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp, Alignment.End)
        ) {
            /** Dummy digit so that the last 2 keys take up the space directly on the right */
            Box(modifier = Modifier.size(108.dp)) {}
            DigitKey(digit = KeyButtons.ZERO,
                onKeyClicked = { key ->
                    onKeyClicked(key)
                })
            BackspaceKey { key ->
                onKeyClicked(key)
            }
        }
    }
}


@Composable
fun DigitKey(modifier: Modifier = Modifier, digit: KeyButtons, onKeyClicked: (KeyButtons) -> Unit) {
    Box(
        modifier = modifier
            .size(size = 108.dp)
            .clip(RoundedCornerShape(size = 32.dp))
            .background(color = PrimaryFixed)
            .clickable {
                onKeyClicked(digit)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = digit.key,
            color = OnPrimaryFixed,
            fontSize = 32.sp,
            fontWeight = FontWeight.W600
        )
    }
}

@Composable
fun BackspaceKey(modifier: Modifier = Modifier, onKeyClicked: (KeyButtons) -> Unit) {
    Box(
        modifier = modifier
            .size(size = 108.dp)
            .clip(RoundedCornerShape(size = 32.dp))
            .background(color = PrimaryFixed.copy(0.3f))
            .clickable {
                onKeyClicked(KeyButtons.BACKSPACE)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = modifier.size(size = 40.dp),
            imageVector = vectorResource(resource = Res.drawable.backspace),
            contentDescription = "Backspace",
            tint = OnPrimaryFixed
        )
    }
}