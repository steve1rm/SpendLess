@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.spendless.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.core.presentation.OnSurface

@Composable
fun SpendLessTheme(
    modifier: Modifier = Modifier,
    toolBarTitle: String,
    onNavigationClicked: () -> Unit,
    content: @Composable (paddingValue: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.background(color = Background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = toolBarTitle,
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        color = OnSurface)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigationClicked()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}