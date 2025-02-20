@file:OptIn(ExperimentalMaterial3Api::class)

package me.androidbox.spendless.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.core.presentation.Error
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.OnSurfaceVariant
import me.androidbox.spendless.settings.presentation.components.SettingsButton
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.lock
import spendless.composeapp.generated.resources.logout
import spendless.composeapp.generated.resources.settings

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .background(color = Background)
            .windowInsetsPadding(insets = WindowInsets.statusBars),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.W600,
                        fontSize = 20.sp,
                        color = OnSurface)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
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
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color(0xff1800401A).copy(alpha = 0.1f)
                    )
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))) {

                    SettingsButton(
                        icon = {
                            Icon(imageVector = vectorResource(resource = Res.drawable.settings),
                                contentDescription = "Go to preferences",
                                tint = OnSurfaceVariant
                            )
                        },
                        text = "Preferences",
                        onClicked = {
                            println("Preferences clicked")
                        }
                    )

                    SettingsButton(
                        icon = {
                            Icon(imageVector = vectorResource(resource = Res.drawable.lock),
                                contentDescription = "Go to security",
                                tint = OnSurfaceVariant)
                        },
                        text = "Security",
                        onClicked = {
                            println("security clicked")
                        }
                    )
                }

                SettingsButton(
                    modifier = Modifier.shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color(0xff1800401A).copy(alpha = 0.1f)
                    ),
                    icon = {
                        Icon(imageVector = vectorResource(resource = Res.drawable.logout),
                            contentDescription = "Go to settings",
                            tint = Error
                        )
                    },
                    text = "Log out",
                    onClicked = {
                        println("Logout clicked")
                    }
                )
            }
        }
    )
}