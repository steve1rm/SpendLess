import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.androidbox.spendless.App
import me.androidbox.spendless.authentication.data.User
import me.androidbox.spendless.authentication.data.UserDao
import me.androidbox.spendless.authentication.domain.imp.generatePinDigest
import me.androidbox.spendless.core.data.SpendLessDatabase
import org.junit.After
import org.junit.Before
import kotlin.test.Test

class InstrumentedSampleTest {
    private lateinit var user: User
    private lateinit var userDao: UserDao
    private lateinit var database: SpendLessDatabase

    @Before
    fun setup() {
        GlobalScope.launch(Dispatchers.Main) {
            val pinHash = generatePinDigest("gg", "12345")

            user = User(
                1,
                "gg",
                pinHash
            )
            database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                SpendLessDatabase::class.java
            ).allowMainThreadQueries().build()
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
        database.close()
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