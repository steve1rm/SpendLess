@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.spendless.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnSurface
import org.jetbrains.compose.resources.vectorResource

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onSettingsClicked: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Steve Mason"
                    )
                },
                actions = {
                    IconButton(
                        modifier = modifier.background(color = OnSurface, RoundedCornerShape(50.dp)),
                        onClick = onSettingsClicked
                    ) {
                        Icon(
                            imageVector = vectorResource(),
                            contentDescription = "navigate to settings"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                DashboardHeader()
            }
        },
        floatingActionButton = {

        },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
fun DashboardHeader(
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(
            text = "$10,382.45",
            fontSize = 46.sp,
            fontWeight = FontWeight.W600,
            color = OnPrimary
        )

        Text(
            text = "Account Balance",
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            color = OnPrimary
        )
    }
}