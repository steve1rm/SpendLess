@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.spendless.transactions.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.Error
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.Primary
import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.core.presentation.components.CurrencyDropDownItem
import me.androidbox.spendless.core.presentation.components.GenericDropDownMenu
import me.androidbox.spendless.onboarding.screens.components.ButtonPanel
import me.androidbox.spendless.transactions.TransactionAction
import me.androidbox.spendless.transactions.TransactionState
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.trending_down
import spendless.composeapp.generated.resources.trending_up

@Composable
fun CreateTransactionContent(
    modifier: Modifier = Modifier,
    state: TransactionState,
    action: (action: TransactionAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonPanel(
            items = listOf(TransactionType.RECEIVER.recipient, TransactionType.SENDER.recipient),
            startIcons = listOf(Res.drawable.trending_down, Res.drawable.trending_up),
            selectedColor = Primary,
            unselectedColor = OnPrimaryFixed
        ) { item ->
            when (item) {
                TransactionType.RECEIVER.recipient -> {
                    action(TransactionAction.OnTransactionTypeClicked(TransactionType.RECEIVER))
                }
                TransactionType.SENDER.recipient -> {
                    action(TransactionAction.OnTransactionTypeClicked(TransactionType.SENDER))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color(0xffC1C3CE)
            ),
            onValueChange = { newName ->
                val name = newName.filter { it.isLetterOrDigit() }

                if(name.count() in 4..14) {
                    action(TransactionAction.OnTransactionAmountEntered(name.trim()))
                }
            },
            value = state.name,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                color = OnSurface.copy(alpha = 0.6f)
            ),
            placeholder = {
                Text(
                    text = state.type.typeName,
                    fontSize = 16.sp,
                    color = OnSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.W600)
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "-$",
                fontSize = 36.sp,
                color = Error,
                fontWeight = FontWeight.W600
            )

            TextField(
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xffC1C3CE)
                ),
                onValueChange = { newAmount ->
                    val amount = newAmount.filter { it.isLetterOrDigit() }

                    action(TransactionAction.OnTransactionAmountEntered(amount.trim()))
                },
                value = state.amount,
                textStyle = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.W600,
                    color = OnSurface
                ),
                placeholder = {
                    Text(
                        text = state.amount,
                        fontSize = 16.sp,
                        color = OnSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.W600)
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {}
            ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add notes",
                    tint = OnSurface)
            }

            Text(
                text = "Add Note",
                fontSize = 16.sp,
                color = OnSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.W400
            )
        }

        var shouldShowDropDown by remember {
            mutableStateOf(false)
        }

        val options = listOf("Entertainment", "Clothing & Accessories", "Education", "Food & Groceries", "Health & Wellness")

        var selectedItem by remember {
            mutableStateOf(options.first())
        }

        if(state.type == TransactionType.RECEIVER) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(48.dp)
                        .shadow(elevation = 2.dp, shape = RoundedCornerShape(16.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                        .padding(start = 12.dp, end = 10.dp)
                        .clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource(),
                            onClick = {
                                shouldShowDropDown = !shouldShowDropDown
                            }
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedItem
                    )

                    Icon(
                        imageVector = if (shouldShowDropDown) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "open close dropdown"
                    )
                }

                if (shouldShowDropDown) {
                    GenericDropDownMenu(
                        dropDownMenuItems = options,
                        onDismissed = {
                            shouldShowDropDown = false
                        },
                        onMenuItemClicked = { item, index ->
                            println("Transaction item $item")
                            selectedItem = item
                        },
                        itemContent = { item ->
                            CurrencyDropDownItem(
                                text = item,
                                isSelected = false
                            )
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary
            ),
            onClick = {
                if(state.amount.count() in 4..14) {
                    action(TransactionAction.OnCreateClicked)
                }
            }
        ) {
            Text(
                text = "Create",
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                color = OnPrimary
            )
        }
    }
}