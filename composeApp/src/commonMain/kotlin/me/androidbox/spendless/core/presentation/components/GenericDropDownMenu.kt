package me.androidbox.spendless.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import me.androidbox.spendless.core.presentation.PrimaryFixed
import me.androidbox.spendless.core.presentation.TransactionItems
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.health

@Composable
fun <T> GenericDropDownMenu(
    modifier: Modifier = Modifier,
    dropDownMenuItems: List<T>,
    onMenuItemClicked: (item: T, index: Int) -> Unit,
    onDismissed: () -> Unit,
    shouldShowDropdown: Boolean,
    startIconList: List<@Composable () -> Unit> = emptyList(),
    itemContent: @Composable (item: T) -> Unit
) {

    val scrollState = rememberScrollState()

    DropdownMenu(
        modifier = modifier,
        scrollState = scrollState,
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        expanded = shouldShowDropdown,
        onDismissRequest = {
            onDismissed()
        },
        content = {
            dropDownMenuItems.forEachIndexed { index, item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    onClick = {
                        onMenuItemClicked(item, index)
                        onDismissed()
                    },
                    text = {
                        itemContent(item)
                    }
                )
            }
        }
    )
}

@Composable
fun TransactionDropDownItem(
    modifier: Modifier = Modifier,
    endIcon: (@Composable () -> Unit)? = null,
    transactionItems: TransactionItems,
    isSelected: Boolean
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        /** Start Items */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(color = PrimaryFixed, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(transactionItems.iconRes),
                    contentDescription = null,
                    tint = Color.Unspecified)
            }

            Text(
                text = transactionItems.title
            )
        }

        /** End Items */
        if(endIcon != null) {
            if (isSelected) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    endIcon()
                }
            }
        }
    }
}


@Composable
fun CurrencyDropDownItem(
    modifier: Modifier = Modifier,
    startIcon: (@Composable () -> Unit)? = null,
    endIcon: (@Composable () -> Unit)? = null,
    text: String,
    isSelected: Boolean
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        /** Start Items */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            startIcon?.invoke()

            Text(
                text = text
            )
        }

        /** End Items */
        if(endIcon != null) {
            if (isSelected) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    endIcon()
                }
            }
        }
    }
}
