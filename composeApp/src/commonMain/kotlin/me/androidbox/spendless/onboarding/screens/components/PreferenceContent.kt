package me.androidbox.spendless.onboarding.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.Currency
import me.androidbox.spendless.core.presentation.DecimalSeparator
import me.androidbox.spendless.core.presentation.ExpensesFormat
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.OnSurfaceVariant
import me.androidbox.spendless.core.presentation.SurfaceContainer
import me.androidbox.spendless.core.presentation.ThousandsSeparator
import me.androidbox.spendless.core.presentation.components.CurrencyDropDownItem
import me.androidbox.spendless.core.presentation.components.GenericDropDownMenu
import me.androidbox.spendless.formatMoney
import me.androidbox.spendless.onboarding.screens.OnboardingPreferenceAction
import me.androidbox.spendless.onboarding.screens.OnboardingPreferenceState

@Composable
fun PreferenceContent(
    modifier: Modifier = Modifier,
    preferenceState: OnboardingPreferenceState,
    action: (action: OnboardingPreferenceAction) -> Unit) {

    var expensesFormat by remember {
        mutableStateOf(ExpensesFormat.NEGATIVE)
    }

    var shouldShowDropDown by remember {
        mutableStateOf(false)
    }

    var selectedCurrency by remember {
        mutableStateOf(Currency.entries.toList().first())
    }

    /** Summary box */
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = SurfaceContainer, RoundedCornerShape(16.dp)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SurfaceContainer),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = preferenceState.money.formatMoney(
                        currency = selectedCurrency,
                        expensesFormat = expensesFormat,
                        thousandsSeparator = preferenceState.thousandsSeparator,
                        decimalSeparator = preferenceState.decimalSeparator
                    ),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W600,
                    color = OnSurface,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "spend this month",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    color = OnSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    /** Expense format */
    Text(
        text = "Expenses format",
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        color = OnSurface
    )

    ButtonPanel(
        items = ExpensesFormat.entries.toList(),
        selectedColor = OnSurface,
        unselectedColor = OnPrimaryFixed.copy(alpha = 0.7f),
        onItemClicked = { item ->
            println(item)
            expensesFormat = item as ExpensesFormat
        })

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Currency",
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = OnSurface
            )

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
                    modifier = Modifier,
                    text = "${selectedCurrency.symbol} ${selectedCurrency.title}"
                )

                Icon(
                    imageVector = if (shouldShowDropDown) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "open close dropdown"
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
        }

        GenericDropDownMenu(
            dropDownMenuItems = Currency.entries,
            onDismissed = {
                shouldShowDropDown = false
            },
            onMenuItemClicked = { item, index ->
                selectedCurrency = item
            },
            itemContent = { currency ->
                CurrencyDropDownItem(
                    text = currency.title,
                    isSelected = false
                )
            },
            shouldShowDropdown = shouldShowDropDown
        )
    }

    Text(
        text = "Decimal separator",
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        color = OnSurface
    )

    ButtonPanel(
        items = DecimalSeparator.entries,
        selectedColor = OnSurface,
        unselectedColor = OnPrimaryFixed.copy(alpha = 0.7f),
        onItemClicked = { preferenceType ->
            println(preferenceType)
            action(OnboardingPreferenceAction.OnDecimalSeparatorSelected(preferenceType as DecimalSeparator))
        }
    )

    Text(
        text = "Thousands separator",
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        color = OnSurface
    )

    ButtonPanel(items = ThousandsSeparator.entries,
        selectedColor = OnSurface,
        unselectedColor = OnPrimaryFixed.copy(alpha = 0.7f),
        onItemClicked = { preferenceType ->
            println(preferenceType)
            action(OnboardingPreferenceAction.OnThousandsSeparatorSelected(preferenceType as ThousandsSeparator))
        })
}