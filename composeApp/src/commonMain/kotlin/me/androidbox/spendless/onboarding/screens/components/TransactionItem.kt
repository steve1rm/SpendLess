package me.androidbox.spendless.onboarding.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.core.presentation.Error
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.PrimaryFixed
import me.androidbox.spendless.core.presentation.Success
import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.core.presentation.formatMoney
import me.androidbox.spendless.formatMoney
import me.androidbox.spendless.onboarding.screens.PreferenceState
import me.androidbox.spendless.settings.data.PreferenceTable
import me.androidbox.spendless.transactions.data.Transaction
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.cash
import spendless.composeapp.generated.resources.cashbag
import spendless.composeapp.generated.resources.notes

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    preferenceState: PreferenceState
) {
    Column(modifier = modifier.fillMaxWidth().background(color = Background)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(color = PrimaryFixed, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = if(transaction.type == TransactionType.RECEIVER) painterResource(transaction.category.iconRes) else painterResource(resource = Res.drawable.cashbag),
                    contentDescription = null,
                    tint = Color.Unspecified
                )

                if (transaction.note.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
                            .align(alignment = Alignment.BottomEnd)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(14.dp)
                                .align(alignment = Alignment.Center),
                            imageVector = vectorResource(resource = Res.drawable.notes),
                            contentDescription = "Expand note",
                            tint = Color.Green
                        )
                    }
                }
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
                        text = transaction.name,
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                        color = OnSurface
                    )

                    Text(
                        text = transaction.category.title,
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        color = OnSurface
                    )
                }

                Text(
                    text = transaction.amount.toDouble().formatMoney(
                        currency = preferenceState.currency,
                        expensesFormat = preferenceState.expensesFormat,
                        decimalSeparator = preferenceState.decimalSeparator,
                        thousandsSeparator = preferenceState.thousandsSeparator
                    ),
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp,
                    color = if(transaction.type == TransactionType.RECEIVER) Error else Color.Green
                )
            }
        }

        Row {
            Spacer(modifier = Modifier.width(52.dp))

            Text(
                text = transaction.note,
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
   /* TransactionItem(
    ) */
}