package me.androidbox.spendless.onboarding.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.unit.dp
import me.androidbox.spendless.core.presentation.Currency
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.logout

@Composable
fun DropDownCurrencyMenu(
    modifier: Modifier = Modifier,
    dropDownMenuItems: List<Currency>,
    onMenuItemClicked: (item: Currency, index: Int) -> Unit,
    onDismissed: () -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    DropdownMenu(
        modifier = modifier.fillMaxWidth(),
        expanded = !isExpanded,
        onDismissRequest = {
            isExpanded = false
            onDismissed()
        },
        content = {
            dropDownMenuItems.forEachIndexed { index, currency ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    onClick = {
                        onMenuItemClicked(currency, index)
                        onDismissed()
                    },
                    text = {
                        DropDownItem(
                           /* icon = {
                                Icon(
                                    imageVector = vectorResource(resource = Res.drawable.logout),
                                    contentDescription = currency.title,
                                    tint = Color.Unspecified
                                )
                            },*/
                            description = currency.title,
                            isSelected = false
                        )
                    }
                )
            }
        }
    )
}

@Composable
fun DropDownItem(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    description: String,
    isSelected: Boolean
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            icon?.invoke()

            Text(
                text = description
            )
        }

        if(isSelected) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color(0xff00419C)
                )
            }
        }
    }
}
