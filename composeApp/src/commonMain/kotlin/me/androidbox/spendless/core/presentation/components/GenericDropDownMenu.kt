package me.androidbox.spendless.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun <T> GenericDropDownMenu(
    modifier: Modifier = Modifier,
    dropDownMenuItems: List<T>,
    onMenuItemClicked: (item: T, index: Int) -> Unit,
    onDismissed: () -> Unit,
    itemContent: @Composable (item: T) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()

    DropdownMenu(
        modifier = modifier.fillMaxWidth(),
        scrollState = scrollState,
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        expanded = !isExpanded,
        onDismissRequest = {
            isExpanded = false
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
