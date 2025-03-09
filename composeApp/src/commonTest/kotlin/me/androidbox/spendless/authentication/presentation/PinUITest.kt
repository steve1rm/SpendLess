package me.androidbox.spendless.authentication.presentation

import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.runTest
import me.androidbox.spendless.authentication.presentation.screens.PinPromptScreen
import kotlin.test.BeforeTest
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.Test

class PinUITest {
    private lateinit var pinViewModel: FakePinViewModel

    @BeforeTest
    fun setUp() {
        pinViewModel = FakePinViewModel()
    }

    @Test
    fun testPinInput() = runTest {
        pinViewModel.createPinState
        assertEquals(
            message = "abc",
            expected = 0,
            actual = pinViewModel.createPinState.value.attempts
        )
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testPinScreen() = runComposeUiTest {
        setContent {
            val pinState by pinViewModel.createPinState.collectAsStateWithLifecycle()

            PinPromptScreen(
                createPinState = pinState,
                onAction = pinViewModel::onAction
            )
        }
    }
}

class FakePinViewModel  : PinViewModel() {
    val _createPinState = MutableStateFlow(CreatePinState())
    override val createPinState = _createPinState.asStateFlow()
}

