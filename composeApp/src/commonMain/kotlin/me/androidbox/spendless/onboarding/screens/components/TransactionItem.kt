package me.androidbox.spendless.onboarding.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.Success
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.health
import spendless.composeapp.generated.resources.notes
import spendless.composeapp.generated.resources.settings

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.size(44.dp)
                    .wrapContentWidth(align = Alignment.CenterHorizontally)
            ) {
                Icon(
                    modifier = Modifier.size(44.dp),
                    painter = painterResource(Res.drawable.health),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(alignment = Alignment.BottomEnd),
                    imageVector = vectorResource(resource = Res.drawable.notes),
                    contentDescription = "Expand note",
                    tint = Color.Green
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier.height(44.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                
                ) {
                    Text(
                        text = "Rick's share - Birthday M.",
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        color = OnSurface
                    )

                    Text(
                        text = "Income",
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = OnSurface
                    )
                }

                Text(
                    text = "$20.00",
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp,
                    color = Success
                )
            }
        }

        Row {
            Spacer(modifier = Modifier.width(52.dp))

            Text(
                text = "Enjoyed a coffee and a snack at Starbucks with Rick and M.",
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                color = OnSurface
            )
        }
    }
}

@Preview()
@Composable
fun TransactionItemPreview() {
    TransactionItem(
    )
}