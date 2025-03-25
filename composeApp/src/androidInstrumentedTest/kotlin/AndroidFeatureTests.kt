import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlinx.coroutines.runBlocking
import me.androidbox.spendless.App
import me.androidbox.spendless.androidSpecificModule
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.data.UserDao
import me.androidbox.spendless.authentication.domain.imp.generatePinDigest
import me.androidbox.spendless.core.data.SpendLessDatabase
import org.junit.After
import org.junit.Before
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.inject
import kotlin.test.Test


class InstrumentedSampleTest: KoinTest {
    private lateinit var user: User
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        runBlocking {
            val pinHash = generatePinDigest("gg", "12345")

            user = User(
                1,
                "gg",
                pinHash
            )

            startKoin {
                modules(
                    androidSpecificModule,
                )
            }
            val database: SpendLessDatabase by inject()


            userDao = database.userDao()

            userDao.insertUser(user)
            val success = userDao.validateUser(user.username, user.pin)
            println("@Before test user creation: $success")
            val success2 = userDao.validateUser("non-user", user.pin)
            println("@Before test invalid user creation: $success2")
        }
    }

    @After
    fun teardown() {
        runBlocking {
            val successAfter = userDao.validateUser(user.username, user.pin)
            println("@After test check for user: $successAfter")
        }
        database.close()
        stopKoin()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testLogin() = runComposeUiTest {
        setContent {
            App()
        }

        onNodeWithContentDescription("inputUsername").performTextInput("gg")
        onNodeWithContentDescription("inputPIN").performTextInput("12345")
        onNodeWithText("Login").performClick()

        waitForIdle()

        onNodeWithContentDescription("btnNewTransaction").performClick()
    }
}
