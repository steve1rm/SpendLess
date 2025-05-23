package me.androidbox.spendless.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import me.androidbox.spendless.core.presentation.ExpiryDuration
import me.androidbox.spendless.core.presentation.LockedDuration
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.Primary
import me.androidbox.spendless.onboarding.screens.components.ButtonPanel
import me.androidbox.spendless.settings.presentation.components.SpendLessTheme

@Composable
fun SecurityScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit) {

    SpendLessTheme(
        modifier = modifier,
        toolBarTitle = "Security",
        onNavigationClicked = onBackClicked,
        content = { paddingValue ->
            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .padding(horizontal = 16.dp)
            ) {

                Text(
                    text = "Session expiry duration",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    color = OnSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                ButtonPanel(
                    items = ExpiryDuration.entries,
                    selectedColor = OnSurface,
                    unselectedColor = OnPrimaryFixed.copy(alpha = 0.7f),
                    onItemClicked = {
                        println(it)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Session expiry duration",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W500,
                    color = OnSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                ButtonPanel(
                    items = LockedDuration.entries,
                    selectedColor = OnSurface,
                    unselectedColor = OnPrimaryFixed.copy(alpha = 0.7f),
                    onItemClicked = {
                        println(it)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    onClick = {
                        println("Save")
                    }
                ) {
                    Text(
                        text = "Save",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        color = OnPrimary
                    )
                }
            }
        }
    )
}