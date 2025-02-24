package me.androidbox.spendless.authentication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.authentication.presentation.AuthenticationAction
import me.androidbox.spendless.authentication.presentation.AuthenticationState
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.OnSurfaceVariant
import me.androidbox.spendless.core.presentation.Primary
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.logo

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    action: (action: AuthenticationAction) -> Unit
) {

    var usernameBorderColor by remember {
        mutableStateOf(Color.Transparent)
    }

    Scaffold(
        modifier = modifier.
        background(color = Background)
            .padding(horizontal = 16.dp)
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Icon(
                imageVector = vectorResource(resource = Res.drawable.logo),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Welcome to SpendLess!",
                fontWeight = FontWeight.W600,
                fontSize = 28.sp,
                color = OnSurface
            )

            Text(
                text = "How can we address you?",
                fontWeight = FontWeight.W600,
                fontSize = 28.sp,
                color = OnSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Create unique username",
                fontWeight = FontWeight.W400,
                fontSize = 16.sp,
                color = OnSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = usernameBorderColor, shape = RoundedCornerShape(16.dp))
                    .onFocusChanged { focusState ->
                        usernameBorderColor = if(focusState.isFocused) {
                            Primary
                        }
                        else {
                            Color.Transparent
                        }
                    },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xffC1C3CE)
                ),
                shape = RoundedCornerShape(16.dp),
                onValueChange = { newName ->
                    action(AuthenticationAction.OnUsernameEntered(newName.trim()))
                },
                value = authenticationState.username,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = OnSurface.copy(alpha = 0.6f)
                ),
                placeholder = {
                    Text(
                        text = "Username",
                        fontSize = 16.sp,
                        color = OnSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.W400
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                onClick = {}
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Next",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        color = OnPrimary
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Go next"
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Already have an account?",
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                color = Primary
            )
        }
    }
}