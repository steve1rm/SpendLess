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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.Primary
import me.androidbox.spendless.core.presentation.PrimaryFixed
import me.androidbox.spendless.core.presentation.ThousandsSeparator
import me.androidbox.spendless.core.presentation.TransactionItems
import me.androidbox.spendless.core.presentation.TransactionType
import me.androidbox.spendless.core.presentation.components.GenericDropDownMenu
import me.androidbox.spendless.core.presentation.components.TransactionDropDownItem
import me.androidbox.spendless.dashboard.DashboardAction
import me.androidbox.spendless.dashboard.DashboardState
import me.androidbox.spendless.formatMoney
import me.androidbox.spendless.onboarding.screens.components.ButtonPanel
import me.androidbox.spendless.transactions.TransactionAction
import org.jetbrains.compose.resources.painterResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.trending_down
import spendless.composeapp.generated.resources.trending_up

fun String.toAmount(): Double {
    val amount = this
        .filter { it.isDigit() }
        .ifEmpty { "0" }
        .toDouble() / 100

    return amount
}

@Composable
fun CreateTransactionContent(
    modifier: Modifier = Modifier,
    state: DashboardState,
    action: (action: DashboardAction) -> Unit,
    openTransaction: (shouldOpen: Boolean) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    val focusRequesterReceiverText = remember {
        FocusRequester()
    }

    val focusRequesterAddNoteText = remember {
        FocusRequester()
    }

    LaunchedEffect(focusRequesterReceiverText) {
        focusRequesterReceiverText.requestFocus()
    }

    val density = LocalDensity.current
    var dropDownItemWidth by remember {
        mutableStateOf(0.dp)
    }

    val visualTransformation = remember {
        CurrencyAmountInputVisualTransformation(
            currency = state.preferenceState.currency,
            expensesFormat = state.preferenceState.expensesFormat,
            thousandsSeparator = state.preferenceState.thousandsSeparator,
            decimalSeparator = state.preferenceState.decimalSeparator
        )
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
                .focusRequester(focusRequesterReceiverText),
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
            BasicTextField(
                visualTransformation = visualTransformation,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                singleLine = true,
                value = state.transaction.amount,
                onValueChange = { newAmount ->
                    val rawAmountInput = newAmount.filter { it.isDigit() }
                    action(DashboardAction.OnTransactionAmountEntered(rawAmountInput))
                },
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.W600,
                    color = OnSurface
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        action(DashboardAction.ShouldShowTransactionNote(shouldShowNote = true))

                        coroutineScope.launch {
                            delay(100)
                            focusRequesterAddNoteText.requestFocus()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add notes",
                        tint = OnSurface
                    )
                }

                Text(
                    text = "Add Note",
                    fontSize = 16.sp,
                    color = OnSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.W400
                )
            }

            if(state.showInputNote) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequesterAddNoteText),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedLabelColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color(0xffC1C3CE)
                    ),
                    onValueChange = { newNote ->
                        action(DashboardAction.OnTransactionNoteEntered(newNote))
                    },
                    value = state.transaction.note,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        color = OnSurface,
                        textAlign = TextAlign.Center
                    ),
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Enter note here",
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
            }
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
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

class CurrencyAmountInputVisualTransformation(
    private val currency: Currency,
    private val expensesFormat: ExpensesFormat,
    private val thousandsSeparator: ThousandsSeparator,
    private val decimalSeparator: DecimalSeparator
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        // 'text' here is the raw digits string from the TextField's state (e.g., "4500").
        val rawDigits = text.text

        // Step 1: Convert the raw digits string into the numerical amount (e.g., "4500" -> 45.00).
        // Ensure the String.toAmount() function is accessible here.
        val numericalAmountValue: Double = rawDigits.toAmount()

        // Step 2: Format the numerical amount into the desired currency display string.
        // Ensure the Double.formatMoney() function is accessible and correctly implemented.
        val formattedAmountString: AnnotatedString = numericalAmountValue.formatMoney(
            currency = currency,
            expensesFormat = expensesFormat,
            thousandsSeparator = thousandsSeparator,
            decimalSeparator = decimalSeparator
        )

        // Step 3: Define how cursor positions map between the raw digits ("4500")
        // and the formatted text ("$45.00"). This is crucial for typing and deleting.
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // 'offset' is the cursor position in the raw digits string ("4500").
                // This simple version maps any position in the raw digits
                // to the end of the formatted string. Works well for append-only.
                // For more complex editing (inserting/deleting in the middle),
                // a more sophisticated mapping analyzing the structure of
                // 'formattedAmountString' vs 'rawDigits' is needed.
                return formattedAmountString.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                // 'offset' is the cursor position in the formatted string ("$45.00").
                // Map any position back to the end of the raw digits string.
                return rawDigits.length
            }
        }

        // Step 4: Return the result containing the text to display and the mapping.
        return TransformedText(
            text = formattedAmountString, // The formatted string ($45.00) the user sees.
            offsetMapping = offsetMapping
        )
    }
}
