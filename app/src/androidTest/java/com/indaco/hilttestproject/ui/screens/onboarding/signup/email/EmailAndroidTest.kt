package com.indaco.hilttestproject.ui.screens.onboarding.signup.email

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.SmallTest
import com.google.android.material.textfield.TextInputLayout
import com.indaco.hilttestproject.HiltTestActivity
import com.indaco.hilttestproject.data.storage.cache.UserCache
import com.indaco.testutils.hilt.launchFragmentInHiltContainer
import com.indaco.testutils.utils.Const
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.core.hilt.modules.storage.CacheModule
import com.indaco.hilttestproject.data.model.User

@UninstallModules(CacheModule::class)
@HiltAndroidTest
@SmallTest
class EmailAndroidTest {

    private val EMAIL_VALID_ENTRY = Pair(Const.EMAIL_VALID, null)
    private val EMAIL_INVALID_ENTRY = Pair(Const.EMAIL_INVALID, R.string.error_email_not_email_pattern)
    private val EMAIL_BLANK_ENTRY = Pair(Const.BLANK, R.string.error_email_blank)

    // allows access to values in res folder
    val res: Resources = ApplicationProvider.getApplicationContext<Context>().resources

    @BindValue
    @JvmField
    var mockUserCache: UserCache = mockk(relaxed = true)

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun email_blank_error_message() {
        // Can't launch fragment in regular container since it's annotated by Hilt
        // @AndroidEntryPoint, the host Activity needs to be @AndroidEntryPoint too
        launchFragmentInHiltContainer<EmailFragment, HiltTestActivity>()

        emailErrorMessageTest(EMAIL_BLANK_ENTRY)
    }

    @Test
    fun email_invalid_error_message() {
        // tell userCache to just null if this function is called (don't execute function)
        every { mockUserCache.getUser(EMAIL_INVALID_ENTRY.first) } returns null
        launchFragmentInHiltContainer<EmailFragment, HiltTestActivity>()

        emailErrorMessageTest(EMAIL_INVALID_ENTRY)
    }

    @Test
    fun email_valid_no_error_message() {
        every { mockUserCache.getUser(EMAIL_VALID_ENTRY.first) } returns null
        launchFragmentInHiltContainer<EmailFragment, HiltTestActivity>()

        emailErrorMessageTest(EMAIL_VALID_ENTRY)
    }

    fun emailErrorMessageTest(entry: Pair<String, Int?>) {
        // Set-up: click on email field, enter email input, and press "Next" button on keyboard
        onView(ViewMatchers.withId(R.id.email))
            .perform(typeText(entry.first))
            .perform(ViewActions.pressImeActionButton())

        val expectedError = if (entry.second != null) res.getString(entry.second!!) else null

        // test error message expected from InputTextLayout
        onView(ViewMatchers.withId(R.id.email_layout)).check { view, _ ->
            val actualError = (view as TextInputLayout).error
            assertEquals(actualError, expectedError)
        }
    }

    @Test
    fun email_in_use_dialog_displayed() {
        val email = EMAIL_VALID_ENTRY.first
        every { mockUserCache.getUser(email) } returns User(email)
        launchFragmentInHiltContainer<EmailFragment, HiltTestActivity>()

        onView(ViewMatchers.withId(R.id.email))
            .perform(typeText(email))
            .perform(ViewActions.pressImeActionButton())

        // Checks that AlertDialog is displayed from function 'showError(error: String)'
        // for better test, know the exact error message
        onView(ViewMatchers.withText(R.string.error_title))
            .inRoot(RootMatchers.isDialog())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
