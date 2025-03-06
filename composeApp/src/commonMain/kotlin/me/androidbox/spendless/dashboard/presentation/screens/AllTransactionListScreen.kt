@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.spendless.dashboard.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.onboarding.screens.components.TransactionItem
import me.androidbox.spendless.transactions.TransactionState

@Composable
fun AllTransactionListScreen(
    transactionState: TransactionState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Transactions",
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        color = OnSurface)
                },
                navigationIcon = {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back to dashboard")
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = transactionState.listOfTransactions,
                    itemContent = { transaction ->
                        TransactionItem(
                            transaction = transaction
                        )
                    }
                )
            }
        })
}