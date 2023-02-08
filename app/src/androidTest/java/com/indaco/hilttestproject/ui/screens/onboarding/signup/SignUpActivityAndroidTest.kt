package com.indaco.hilttestproject.ui.screens.onboarding.signup

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.indaco.mylibrary.di.espresso.TestCountingIdlingResource
import com.indaco.testutils.hilt.lazyActivityScenarioRule
import com.indaco.mylibrary.di.hilt.modules.storage.CacheModule
import com.indaco.mylibrary.data.storage.cache.UserCache
import com.indaco.hilttestproject.ui.screens.onboarding.signup.email.EmailFragment
import com.indaco.hilttestproject.ui.screens.onboarding.signup.password.PasswordFragment
import com.indaco.testutils.utils.Const
import com.indaco.hilttestproject.R
import com.indaco.hilttestproject.ui.screens.onboarding.welcome.WelcomeActivity
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

@UninstallModules(CacheModule::class)
@HiltAndroidTest
@SmallTest
class SignUpActivityAndroidTest {

    private val intent = Intent(ApplicationProvider.getApplicationContext(), SignUpParentActivity::class.java)

    @BindValue
    @JvmField
    var mockUserCache: UserCache = mockk(relaxed = true)

    @get: Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get: Rule(order = 1)
    val scenarioRule = lazyActivityScenarioRule<SignUpParentActivity>(launchActivity = false)

    private fun launchHiltActivityWithMocks(launchActivity: Boolean = true, everyFunc: (() -> Unit)? = null) {
        everyFunc?.invoke()
        hiltRule.inject()
        if (launchActivity)
            scenarioRule.launch(intent)
    }

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(TestCountingIdlingResource.countingIdlingResource)
        Intents.init()
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(TestCountingIdlingResource.countingIdlingResource)
        Intents.release()
    }

    @Test
    fun emailFragment_active() {
        launchHiltActivityWithMocks()
        scenarioRule.getScenario().onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(it.binding.emailContainer.id)
            Truth.assertThat(fragment is EmailFragment).isTrue()
        }
    }

    @Test
    fun passwordFragment_inactive() {
        launchHiltActivityWithMocks()
        scenarioRule.getScenario().onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(it.binding.passwordContainer.id)
            Truth.assertThat(fragment is PasswordFragment).isFalse()
        }
    }

    @Test
    fun registerButton_inactive() {
        launchHiltActivityWithMocks()
        scenarioRule.getScenario().onActivity {
            Truth.assertThat(it.binding.register.alpha).isLessThan(1f)
            Truth.assertThat(it.binding.register.hasOnClickListeners()).isFalse()
        }
    }

    // Demo testing accessing fragments within activity
    @Test
    fun successful_signup() {
        val email = Const.EMAIL_VALID
        val password = Const.PASSWORD_VALID

        launchHiltActivityWithMocks(true) {
            every { mockUserCache.getUser(email) } returns null
        }

        // from EmailFragment
        Espresso.onView(ViewMatchers.withId(R.id.email))
            .perform(ViewActions.typeText(email))
            .perform(ViewActions.pressImeActionButton())

        // from PasswordFragment
        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText(password))
            .perform(ViewActions.pressImeActionButton())

        Espresso.onView(ViewMatchers.withId(R.id.confirm_password))
            .perform(ViewActions.typeText(password))
            .perform(ViewActions.pressImeActionButton())

        Espresso.onView(ViewMatchers.withId(R.id.register))
            .perform(ViewActions.click())

        // Check that WelcomeActivity is started
        Intents.intended(IntentMatchers.hasComponent(WelcomeActivity::class.java.name))
    }
}