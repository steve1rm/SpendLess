@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.spendless.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnPrimaryFixed
import me.androidbox.spendless.core.presentation.OnSecondaryContainer
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.Primary
import me.androidbox.spendless.core.presentation.PrimaryFixed
import me.androidbox.spendless.core.presentation.SecondaryContainer
import me.androidbox.spendless.core.presentation.SecondaryFixed
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.settings

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onSettingsClicked: () -> Unit,
    onAddTransaction: () -> Unit
) {

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    Brush.radialGradient(
                        colors = listOf(Primary, OnPrimaryFixed),
                        center = Offset(x = 400.dp.value, y = 600.dp.value),
                        radius = 2000f
                    )
                )
            },
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                title = {
                    Text(
                        text = "Steve Mason",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W600,
                        color = OnPrimary
                    )
                },
                actions = {
                    Box(
                        modifier = modifier
                            .size(48.dp)
                            .background(color = Color.White.copy(0.2f), RoundedCornerShape(16.dp))
                            .clickable(
                                onClick = onSettingsClicked
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = vectorResource(resource = Res.drawable.settings),
                            contentDescription = "navigate to settings",
                            tint = OnPrimary.copy(0.8f)
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.fillMaxWidth().padding(paddingValues)
            ) {
                DashboardHeader()
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTransaction,
                containerColor = SecondaryContainer,
                content = {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = "Add new transaction",
                        tint = OnSecondaryContainer
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
fun DashboardHeader(
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
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

    Row(
        modifier = Modifier.fillMaxWidth().wrapContentSize(align = Alignment.BottomCenter),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LargestTransaction(
            modifier = Modifier.weight(1.6f),
            hasEmptyTransaction = false
        )
        PreviousTransaction(
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LargestTransaction(
    modifier: Modifier = Modifier,
    hasEmptyTransaction: Boolean = true
) {
    if(hasEmptyTransaction) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(color = PrimaryFixed, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Your largest transaction will appear here",
                maxLines = 2,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                color = OnSurface
            )
        }
    }
    else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(color = PrimaryFixed, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Adobe Yearly",
                    maxLines = 2,
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp,
                    color = OnSurface
                )

                Text(
                    textAlign = TextAlign.Center,
                    text = "-$59.99",
                    maxLines = 2,
                    fontWeight = FontWeight.W600,
                    fontSize = 20.sp,
                    color = OnSurface
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "Largest transaction",
                    maxLines = 2,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = OnSurface
                )

                Text(
                    textAlign = TextAlign.Center,
                    text = "Jan 7, 2025",
                    maxLines = 2,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    color = OnSurface
                )
            }
        }
    }
}

@Composable
fun PreviousTransaction(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(color = SecondaryFixed, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "-$762.20",
            maxLines = 2,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            color = OnSurface
        )

        Text(
            textAlign = TextAlign.Center,
            text = "Previous week",
            maxLines = 2,
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            color = OnSurface
        )
    }
}