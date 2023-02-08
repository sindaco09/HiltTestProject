package com.indaco.login.ui.screens.login.nativ_login

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.indaco.hilttestproject.ui.screens.onboarding.welcome.WelcomeActivity
import com.indaco.login.R
import com.indaco.login.ui.screens.login.native_login.NativeLoginActivity
import com.indaco.mylibrary.util.BaseString
import com.indaco.testutils.hilt.lazyActivityScenarioRule
import com.indaco.testutils.utils.Const.EMAIL_VALID
import com.indaco.testutils.utils.Const.PASSWORD_VALID
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@SmallTest
class NativeLoginActivityAndroidTest {

    private val intent = Intent(ApplicationProvider.getApplicationContext(), NativeLoginActivity::class.java)

    @get: Rule(order = 1)
    val scenarioRule = lazyActivityScenarioRule<NativeLoginActivity>(launchActivity = false)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun password_is_blank() {
        scenarioRule.launch(intent)

        scenarioRule.getScenario().onActivity {
            with(it.binding) {
                email.setText(EMAIL_VALID)
                password.setText("")

                submit.performClick()

                Truth.assertThat(passwordLayout.error.toString())
                    .isEqualTo(it.getString(BaseString.error_password_blank))
            }
        }
    }

    // Excluding all case scenarios for valid password/email inputs. it can be extracted and
    // unit tested in a separate class

    @Test
    fun invalid_login_credentials() {
        scenarioRule.launch(intent)

        // setup test
        onView(withId(R.id.email))
            .perform(typeText(EMAIL_VALID))
        onView(withId(R.id.password))
            .perform(typeText(PASSWORD_VALID))
        onView(withId(R.id.submit))
            .perform(click())

        // Check if AlertDialog popped up, this looks for a view with the string provided first
        // then checks if it is a dialog and that it is displayed. good for anonymous objects
        onView(withText(BaseString.user_not_found))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))

    }

    @Test
    fun valid_login_credentials() {
        scenarioRule.launch(intent)

        // setup test
        onView(withId(R.id.email))
            .perform(typeText(EMAIL_VALID))
        onView(withId(R.id.password))
            .perform(typeText(PASSWORD_VALID))
        onView(withId(R.id.submit))
            .perform(click())

        // Check that WelcomeActivity is started
        Intents.intended(IntentMatchers.hasComponent(WelcomeActivity::class.java.name))
    }
}