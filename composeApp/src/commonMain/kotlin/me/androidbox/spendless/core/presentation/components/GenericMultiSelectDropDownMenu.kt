package me.androidbox.spendless.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp

@Composable
fun <T> GenericMultiSelectDropDownMenu(
    modifier: Modifier = Modifier,
    dropDownMenuItems: List<T>,
    onMenuItemClicked: (item: T, index: Int) -> Unit,
    onDismissed: () -> Unit,
    multiSelectItem: ((item: T) -> Modifier)? = null,
    itemContent: @Composable (item: T) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    /* MultiSelectDropDownItem(
        modifier = modifier.fillMaxWidth(),
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
                        .padding(4.dp)
                        .then(
                            if(multiSelectItem != null) {
                                multiSelectItem(item)
                            } else {
                                Modifier
                            }
                        ),
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
*/

    /*
multiSelectItem = { item ->
    if(item.isSelected) {
        Modifier.background(color = Color.LightGray.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
    }
    else {
        Modifier.clip(shape = RoundedCornerShape(16.dp))
    }
    )
*/

    @Composable
    fun MultiSelectDropDownItem(
        modifier: Modifier = Modifier,
        startIcon: (@Composable () -> Unit)? = null,
        endIcon: (@Composable () -> Unit)? = null,
        description: String,
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
                    text = description
                )
            }

            /** End Items */
            if (endIcon != null) {
                if (isSelected) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        endIcon()
                        /* Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color(0xff00419C)
                )*/
                    }
                }
            }
        }
    }
}


/* icon = {
                              Icon(
                                  imageVector = vectorResource(resource = Res.drawable.logout),
                                  contentDescription = currency.title,
                                  tint = Color.Unspecified
                              )
                          },*/