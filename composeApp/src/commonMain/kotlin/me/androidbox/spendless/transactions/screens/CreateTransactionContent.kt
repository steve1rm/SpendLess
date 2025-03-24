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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.Error
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.Primary
import me.androidbox.spendless.core.presentation.PrimaryFixed
import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.core.presentation.components.GenericDropDownMenu
import me.androidbox.spendless.core.presentation.components.TransactionDropDownItem
import me.androidbox.spendless.dashboard.DashboardAction
import me.androidbox.spendless.dashboard.DashboardState
import me.androidbox.spendless.onboarding.screens.components.ButtonPanel
import org.jetbrains.compose.resources.painterResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.trending_down
import spendless.composeapp.generated.resources.trending_up

@Composable
fun CreateTransactionContent(
    modifier: Modifier = Modifier,
    state: DashboardState,
    action: (action: DashboardAction) -> Unit,
    openTransaction: (shouldOpen: Boolean) -> Unit
) {

    val focusRequester = remember {
        FocusRequester()
    }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    val density = LocalDensity.current
    var dropDownItemWidth by remember {
        mutableStateOf(0.dp)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Create Transaction",
                fontSize = 16.sp,
                color = OnSurface,
                fontWeight = FontWeight.W600)

            IconButton(
                onClick = {
                    openTransaction(false)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Should close bottom sheet",
                    tint = OnSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ButtonPanel(
            items = listOf(TransactionType.RECEIVER, TransactionType.SENDER),
            startIcons = listOf(Res.drawable.trending_down, Res.drawable.trending_up),
            selectedColor = Primary,
            unselectedColor = OnPrimaryFixed
        ) { item ->
            when (item) {
                TransactionType.RECEIVER -> {
                    action(DashboardAction.OnTransactionTypeClicked(TransactionType.RECEIVER))
                }
                TransactionType.SENDER -> {
                    action(DashboardAction.OnTransactionTypeClicked(TransactionType.SENDER))
                }
                else -> {
                    /* no-op */
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
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
                action(DashboardAction.OnTransactionNameEntered(name.trim()))
            },
            value = state.transaction.name,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                color = OnSurface,
                textAlign = TextAlign.Center
            ),
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.transaction.type.typeName,
                    fontSize = 16.sp,
                    color = OnSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f).padding(end = 2.dp),
                text = if(state.transaction.type == TransactionType.RECEIVER) "-${state.preferenceState.currency.symbol}" else state.preferenceState.currency.symbol ,
                fontSize = 36.sp,
                color = if(state.transaction.type == TransactionType.RECEIVER) Error else Color.Green,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.End
            )

            BasicTextField(
                modifier = Modifier.weight(1.5f),
                maxLines = 1,
                singleLine = true,
                value = state.transaction.amount,
                onValueChange = { newAmount ->
                    val amount = newAmount.filter { it.isLetterOrDigit() }
                    action(DashboardAction.OnTransactionAmountEntered(amount.trim()))
                },
                textStyle = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.W600,
                    color = OnSurface
                ),
                decorationBox = { innerTextField ->
                    if (state.transaction.amount.isEmpty()) {
                        Text(
                            text = "00.00",
                            fontSize = 36.sp,
                            color = OnSurface.copy(alpha = 0.6f),
                            fontWeight = FontWeight.W600
                        )
                    }
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {

                }
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

        if(state.transaction.type == TransactionType.RECEIVER) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .onSizeChanged { intSize ->
                        dropDownItemWidth = with(density) {
                            intSize.width.toDp()
                        }
                    }
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
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = {
                                shouldShowDropDown = !shouldShowDropDown
                            }
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp).background(color = PrimaryFixed, shape = RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(state.transaction.category.iconRes),
                                contentDescription = null,
                                tint = Color.Unspecified)
                        }

                        Text(
                            text = state.transaction.category.title
                        )
                    }

                    Icon(
                        imageVector = if (shouldShowDropDown) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "open close dropdown"
                    )
                }

                if (shouldShowDropDown) {
                    GenericDropDownMenu(
                        modifier = Modifier.width(dropDownItemWidth),
                        dropDownMenuItems = TransactionItems.entries,
                        onDismissed = {
                            shouldShowDropDown = false
                        },
                        onMenuItemClicked = { item, index ->
                            println("Transaction item $item")
                            action(DashboardAction.OnTransactionCategoryChanged(category = item))
                        },
                        itemContent = { item ->
                            TransactionDropDownItem(
                                transactionItems = item,
                                isSelected = false
                            )
                        },
                        shouldShowDropdown = shouldShowDropDown
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
            enabled = state.transaction.name.count() in 4..14,
            onClick = {
                action(DashboardAction.OnCreateClicked)
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