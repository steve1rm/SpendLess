package me.androidbox.spendless.authentication.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.authentication.presentation.LoginAction
import me.androidbox.spendless.authentication.presentation.LoginState
import me.androidbox.spendless.core.presentation.Background
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.OnSurface
import me.androidbox.spendless.core.presentation.OnSurfaceVariant
import me.androidbox.spendless.core.presentation.Primary
import org.jetbrains.compose.resources.vectorResource
import spendless.composeapp.generated.resources.Res
import spendless.composeapp.generated.resources.logo

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginState: LoginState,
    action: (action: LoginAction) -> Unit
) {

    var usernameBorderColor by remember {
        mutableStateOf(Color.Transparent)
    }

    var pinBorderColor by remember {
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
                .fillMaxWidth()
                .fillMaxHeight(),
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
                text = "Welcome Back!",
                fontWeight = FontWeight.W600,
                fontSize = 28.sp,
                color = OnSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your login details",
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
                    action(LoginAction.OnUsernameEntered(newName.trim()))
                },
                value = loginState.username,
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

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = pinBorderColor, shape = RoundedCornerShape(16.dp))
                    .onFocusChanged { focusState ->
                        pinBorderColor = if(focusState.isFocused) {
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
                    action(LoginAction.OnPinEntered(newName.trim()))
                },
                value = loginState.pin,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = OnSurface.copy(alpha = 0.6f)
                ),
                placeholder = {
                    Text(
                        text = "PIN",
                        fontSize = 16.sp,
                        color = OnSurface.copy(alpha = 0.6f),
                        fontWeight = FontWeight.W400
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                onClick = {
                    action(LoginAction.OnLoginClicked)
                }
            ) {
                Text(
                    text = "Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    color = OnPrimary
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                modifier = Modifier.clickable(
                    onClick = {
                        action(LoginAction.OnRegisterClicked)
                    }
                ),
                text = "New to SpendLess?",
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                color = Primary
            )

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(
                modifier = Modifier.wrapContentHeight(Alignment.Bottom),
                visible = loginState.shouldShowRedBanner,
                content = {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(height = 72.dp)
                            .background(color = Color.Red),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Invalid username or PIN",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
        }
    }
}