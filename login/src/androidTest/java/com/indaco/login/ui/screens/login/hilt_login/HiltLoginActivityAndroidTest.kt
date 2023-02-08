package com.indaco.login.ui.screens.login.hilt_login

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
import com.indaco.mylibrary.data.model.User
import com.indaco.mylibrary.data.storage.dao.UserDao
import com.indaco.mylibrary.di.hilt.modules.storage.components.DaoModule
import com.indaco.mylibrary.util.BaseString
import com.indaco.testutils.hilt.lazyActivityScenarioRule
import com.indaco.testutils.utils.Const.EMAIL_INVALID
import com.indaco.testutils.utils.Const.EMAIL_VALID
import com.indaco.testutils.utils.Const.PASSWORD_VALID
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Uninstalling CacheModule doesn't work
 * can i only uninstall modules in the appDependencies component?
 */
@UninstallModules(DaoModule::class)
@HiltAndroidTest
@SmallTest
class HiltLoginActivityAndroidTest {

    private val intent = Intent(ApplicationProvider.getApplicationContext(), HiltLoginActivity::class.java)

    @BindValue
    @JvmField
    var userDao: UserDao = mockk(relaxed = true)

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val scenarioRule = lazyActivityScenarioRule<HiltLoginActivity>(launchActivity = false)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    // Needed AFTER Intents.init() but before the test. apply any 'every' statements here
    private fun launchHiltActivityWithMocks(everyFunc: (() -> Unit)? = null) {
        everyFunc?.invoke()
        hiltRule.inject()
        scenarioRule.launch(intent)
    }

    @Test
    fun password_is_blank() {
        launchHiltActivityWithMocks()

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
        launchHiltActivityWithMocks {
            every { userDao.getUser(EMAIL_INVALID) } returns null
        }

        // setup test
        onView(withId(R.id.email))
            .perform(typeText(EMAIL_INVALID))
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
        launchHiltActivityWithMocks {
            every { userDao.getUser(EMAIL_VALID) } returns User(EMAIL_VALID)
        }

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