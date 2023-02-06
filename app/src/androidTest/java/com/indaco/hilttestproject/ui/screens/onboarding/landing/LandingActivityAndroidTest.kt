package com.indaco.hilttestproject.ui.screens.onboarding.landing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.filters.SmallTest
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.core.hilt.modules.storage.CacheModule
import com.indaco.hilttestproject.core.hilt.modules.storage.components.SharedPreferencesModule
import com.indaco.hilttestproject.data.model.User
import com.indaco.hilttestproject.data.storage.cache.UserCache
import com.indaco.hilttestproject.data.storage.cache.UserCache.Companion.KEY_CURRENT_USER
import com.indaco.hilttestproject.ui.screens.onboarding.welcome.WelcomeActivity
import com.indaco.testutils.hilt.lazyActivityScenarioRule
import com.indaco.testutils.utils.Const.EMAIL_VALID
import com.indaco.testutils.utils.MockSharedPrefsUtil.getMockString
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import org.junit.*
import kotlin.test.assertEquals

/* CacheModule is uninstalled and replaced with mock via @BindValue to manipulate data
 * received by activity
 */
@UninstallModules(CacheModule::class)
@HiltAndroidTest
@SmallTest
class LandingActivityAndroidTest {

    val intent get() = Intent(ApplicationProvider.getApplicationContext(), LandingActivity::class.java)

    // It is easier to mock data source than the viewmodel
    @BindValue
    var mockUserCache: UserCache = mockk(relaxed = true)

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    // renamed to ActivityScenarioExtension in JUnit5
    @get: Rule(order = 1)
    val scenarioRule = lazyActivityScenarioRule<LandingActivity>(launchActivity = false)

    @Before
    fun setup() {
        Intents.init()

        // I want current user to always return null in this specific test
        // unless explicitly testing it
        every { mockUserCache.currentUser } returns null
    }

    // Needed AFTER Intents.init() but before the test. apply any 'every' statements here
    private fun launchHiltActivityWithMocks(everyFunc: (() -> Unit)? = null) {
        everyFunc?.invoke()
        hiltRule.inject()
        scenarioRule.launch(intent)
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test_value_true() {
        launchHiltActivityWithMocks { every { mockUserCache.testValue } returns true }

        scenarioRule.getScenario().onActivity {
            assertEquals(expected = it.getString(R.string.test_value_true),
                actual = it.binding.testValue.text.toString())
        }
    }

    @Test
    fun test_value_false() {
        launchHiltActivityWithMocks { every { mockUserCache.testValue } returns false }

        scenarioRule.getScenario().onActivity {
            assertEquals(expected = it.getString(R.string.test_value_false),
                actual = it.binding.testValue.text.toString())
        }
    }

    @Test
    fun isNotLoggedIn() {
        launchHiltActivityWithMocks { every { mockUserCache.currentUser } returns null }

        // Can access bindings via scenario
        scenarioRule.getScenario().onActivity {
            assertEquals(expected = it.getString(R.string.login_status_false),
                actual = it.binding.loginStatus.text.toString())
        }
    }

    @Test
    fun isLoggedIn() {
        launchHiltActivityWithMocks { every { mockUserCache.currentUser } returns User(EMAIL_VALID) }

        scenarioRule.getScenario().onActivity {
            assertEquals(expected = it.getString(R.string.login_status_success),
                actual = it.binding.loginStatus.text.toString())
        }
    }

    @Test
    fun goToWelcomeScreen() {
        launchHiltActivityWithMocks { every { mockUserCache.currentUser } returns User(EMAIL_VALID) }

        intended(hasComponent(WelcomeActivity::class.java.name))
    }
}